function animateCommentUpdate(elem, msg) {
    $(elem).hide();
    $(elem).html(msg);
    $(elem).slideDown();
}

function commentKeyup(context) {

    $(context).find('textarea').bind('keyup change', function () {
    	var thisCommentBox = getCommentContext(this);
        if (!$(thisCommentBox).find('textarea').prop('readonly')) {
            var message = 'Click the "Save" button to save your comment.',
                elem = $(thisCommentBox).find('form').find('div[data-comment=update]');
            if ($(elem).html() !== message) {
                animateCommentUpdate(elem, message);
            }
        }
    });
}


function commentRenderButton(context) {

    $(context).find('button[data-comment=edit]').button({
        icons: {
            primary: 'ui-icon-pencil'
        },
        label: 'Edit',
        text: true
    }).show();
    $(context).find('button[data-comment=save]').button({
        icons: {
            primary: 'ui-icon-disk'
        },
        label: 'Save',
        text: true
    }).hide().addClass('ui-state-highlight');
    $(context).find('button[data-comment=delete]').button({
        icons: {
            primary: 'ui-icon-trash'
        },
        label: 'Delete',
        text: true
    }).show();
}

function commentAdd(context) {
    $(context).find('button[data-comment=add]').click(function (e) {
        e.preventDefault();
        var thisSection = getCommentSectionContext(this),
            newComment = $(thisSection).find('[data-comment=new]').clone(true);

        $(this).parent().before(newComment);

        $(newComment).attr('data-comment', 'comment');
        
        if ($(newComment).find('form').length == 0) {
        	$(newComment).wrapInner('<form></form>');
        }
        
        $(newComment).show();
        commentKeyup();
        commentRenderButton(newComment);
        $(newComment).find('button[data-comment=edit]').click();

        //return false;
    });
}

function commentEdit(context) {
    $(context).find('button[data-comment=edit]').click(function (e) {
        e.preventDefault();

        //hide the edit button
        $(this).hide();
       
        //find the comment container box
        var thisCommentBox = getCommentContext(this);
        
        //show the save button for this comment box
        $(thisCommentBox).find('[data-comment=save]').show();
        
        //enable the text box and give it focus
        $(thisCommentBox).find('input[name=sequence]').prop('disabled', false);
        $(thisCommentBox).find('textarea').prop('readonly', false).focus();

    });
}

function commentSave(context) {
    $(context).find('button[data-comment=save]').click(function (e) {
        e.preventDefault();

        var el = this;
        //get the comment section container
        var thisComment = getCommentContext(this);
        
        var form = $(thisComment).find('form');
        var data = $(form).serialize();
        var elem = $(form).find('div[data-comment=update]');
        var saveButton = $(form).find('[data-comment=save]');
        

        $(saveButton).button({
            label: 'Saving...'
        });

        $.ajax({
            type: 'POST',
            url: '/web/CtlComments?requestType=updateComment',
            dataType: 'json',
            data: data,
            beforeSend: function () {
                animateCommentUpdate(elem, 'Saving...');
            },
            success: function (data) {
                if (data.success) {
                    animateCommentUpdate(elem, 'Comment saved!');
                    if (data.isNew) {
                        //if this comment is new, set it with the returned UUID
                        $(form).find('input[name=uniqueKey]').val(data.uuid);
                    }

                    $(saveButton).button({
                        label: 'Save'
                    }).hide();
                    $(form).find('[data-comment=edit]').show();
                    
                    var txtArea = $(form).find('textarea'); 
                    $(txtArea).prop('readonly', true);
                    updatePrintBox(txtArea);
                    
                    $(form).find('input[name=sequence]').prop('disabled', true);

                    if ($(form).find("input[name=sequence]").length > 0) {
                    	var section = getCommentSectionContext(el)
                    	var ctrlId = $(section).parent().prev().attr("id");
                    	$("#" + ctrlId).next().children().remove();
                    	$("#" + ctrlId).trigger("comment-reload");
                    }
                    
                } else {
                    elem.html(data.error);
                }
            }
        });


    });
}

function updatePrintBox(textarea) {
	if ($(textarea).val() != '') {
		if ($(textarea).next('pre.print-only').length > 0) {
			$(textarea).next('pre.print-only').html($(textarea).val());
		} else {
			$(textarea).after('<pre class="print-only">' + $(textarea).val() + '</pre>');
		}
	}
	
}

function commentDelete(context) {
    $(context).find('button[data-comment=delete]').click(function (e) {
        e.preventDefault();
        var comment = getCommentContext(this),
            updateBox = $(comment).find('[data-comment=update]'),
            data = $(comment).find('form').serialize();

        if (!confirm('Are you sure want to delete this comment?')) {
            return false;
        }

        $.ajax({
            type: 'POST',
            url: '/web/CtlComments?requestType=deleteComment',
            dataType: 'json',
            data: data,
            beforeSend: function () {
                animateCommentUpdate(updateBox, 'Deleting...');
            },
            success: function (data) {
                if (data.success) {
                    $(comment).remove();
                } else {
                    animateCommentUpdate(updateBox, 'Comment NOT deleted.  ' + data.error);
                }
            }
        });

    });
}


function commentActions(context) {

    commentAdd(context);
    commentEdit(context);
    commentSave(context);
    commentDelete(context);

    commentKeyup(context);
    commentRenderButton(context);

}

function commentAjaxCallback(data, targetId, viewOnly) {
    var target = $('#' + targetId).next();
    $(target).find('[data-comment=loading]').remove();

    $(target).html(data);

    commentActions(target);

    $(target).find('textarea').each(function (i, textarea) {
    	updatePrintBox(textarea);
    });
    
    
    $(target).find('button[data-comment=add]').button({
        icons: {
            primary: 'ui-icon-document'
        }
    });

}

function commentAjaxCall(elem, url, viewOnly) {
    var targetId = $(elem).attr('id');
    
    //move the click handler to a comment-reload handler
    $(elem).bind('comment-reload',$(elem).prop('onclick'));
    $(elem).prop('onclick', null);  //remove inline onclick
    
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'html',
        cache: false,
        success: function (data) {
            commentAjaxCallback(data, targetId, viewOnly);
        }
    });
}

function getCommentContext(el) {
	return $(el).parentsUntil('[data-comment=comment]').parent('[data-comment=comment]');
}
function getCommentSectionContext(el) {
	return $(el).parentsUntil('[data-comment=section]').parent('[data-comment=section]');
}