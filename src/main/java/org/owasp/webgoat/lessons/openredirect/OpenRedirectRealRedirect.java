/*
 * SPDX-FileCopyrightText: Copyright © 2025 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.openredirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Provides a real 302 redirect for experimentation separate from assignment scoring.
 */
@Controller
public class OpenRedirectRealRedirect {

  private static void validateDomain(String domainToValidate, List<String> allowedDomains) {
    if (domainToValidate == null || allowedDomains == null) {
        throw new IllegalArgumentException("Invalid redirect attempt: invalid domain");
    }
    try {
        URI uri = new URI(domainToValidate);
        String scheme = uri.getScheme();
        if (scheme != null && !"https".equals(scheme) && !"http".equals(scheme)) {
            throw new IllegalArgumentException("Invalid redirect attempt: invalid scheme");
        }
        String host = uri.getHost();
        if (host != null && !allowedDomains.contains(host)) {
            throw new IllegalArgumentException("Invalid redirect attempt: domain not in allowlist");
        }
    } catch (URISyntaxException e) {
        throw new IllegalArgumentException("Invalid redirect attempt: malformed domain");
    }
  }

  @GetMapping("/OpenRedirect/realRedirect")
  public ModelAndView real(@RequestParam("url") String url) {
    // Intentionally vulnerable: no validation
    // Allowlist validation; throw an `IllegalArgumentException` if validation failed.
    validateDomain(url, Arrays.asList("MOBB_ALLOWLIST_PLACEHOLDER"));
    return new ModelAndView("redirect:" + url);
  }
}
