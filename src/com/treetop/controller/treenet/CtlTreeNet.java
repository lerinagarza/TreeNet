package com.treetop.controller.treenet;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.treetop.SessionVariables;
import com.treetop.controller.BaseController;
import com.treetop.data.UrlFile;

public class CtlTreeNet extends BaseController {

	@Override
	protected boolean isSecurityEnabled() {
		return true;
	}

	@Override
	protected String defaultRequest(HttpServletRequest request) {
		return listTreeNetMenu(request);
	}

	@Override
	protected String securityUrl(HttpServletRequest request, String requestType) {
		return "/web/TreeNetList";
	}

	@SuppressWarnings({ "unchecked" })
	private String listTreeNetMenu(HttpServletRequest request) {

		Vector<UrlFile> applications = new Vector<UrlFile>();

		try {
			HttpServletResponse response = null;
			String user = SessionVariables.getSessionttiProfile(request, response);
			String[] roles = SessionVariables.getSessionttiUserRoles(request, response);
			String[] groups = SessionVariables.getSessionttiUserGroups(request, response);
			String[] paths = SessionVariables.getSessionttiUserPaths(request, response);
			String[] pubPaths = SessionVariables.getSessionttiUserPubPaths(request, response);
			String addFolder = SessionVariables.getSessionttiUserPubFolders(request, response);

			String path = "";
			String orderby = "";

			String typevalue = request.getParameter("typevalue");
			if (typevalue == null || typevalue.equals("Search...")) {
				typevalue = "";
			}

			String requestType = request.getParameter("type");
			if (requestType == null) {
				requestType = "";
			}

			if (!requestType.equals("search")) {
				requestType = "";
			}

			// if in role 1 or 8, show all links regardless of security status
			boolean displayAll = false;
			for (String role : roles) {
				if (role.equals("1") || role.equals("8")) {
					displayAll = true;
				}
			}

			Vector<UrlFile> majors = new UrlFile().findMajors(roles, requestType);

			for (UrlFile major : majors) {
				boolean addMajor = true;

				Vector<UrlFile> minors = major.findMinorsbyMajor(roles, major.getMajorNumber(), "");

				for (UrlFile minor : minors) {

					Vector<UrlFile> allUrls = minor.findUrlsbyMajMin(roles, groups, paths, pubPaths, addFolder, user,
							major.getUrlNumber(), minor.getUrlNumber(), orderby, path, typevalue, requestType);
					Vector<UrlFile> securedUrls = new Vector<UrlFile>();

					if (displayAll) {
						securedUrls = allUrls;
					} else {
						// push secured urls into the securedUrls vector
						for (int i = 0; i < allUrls.size(); i++) {
							UrlFile url = allUrls.elementAt(i);
							if (url.getCanIUseIt().trim().equals("Y")) {
								securedUrls.addElement(url);
							}
						}
					}

					// only add the minor (header) if there are urls within this
					// minor
					if (!securedUrls.isEmpty()) {
						// if this is the first url to be added, add the major
						if (addMajor) {
							applications.addElement(major);
							addMajor = false;
						}
						applications.addElement(minor);
					}

					// add each url
					for (UrlFile url : securedUrls) {
						//need to encode spaces in the URLs
						String encodedUrl = url.getUrlAddress().trim().replaceAll(" ", "%20");
						url.setUrlAddress(encodedUrl);
						applications.addElement(url);
					} // end loop urls

				} // end loop minors

			} // end loop majors

		} catch (Exception e) {
			System.err.println("Exception @ CtlTreeNet.defaultRequest(request).  " + e);
		}

		request.setAttribute("listTreeNetMenu", applications);

		return "/view/treenet/inqTreeNet.jsp";

	}

}
