package com.sliit.ssd.csrfapp.controllers;

import com.sliit.ssd.csrfapp.models.FundTransfer;
import com.sliit.ssd.csrfapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles fund transfer functions
 *
 * Created by ssudheesan on 9/6/18.
 */
@Controller
public class FundTransferController {

    @Autowired
    AuthService authService;

    @PostMapping("/transfer")
    public String transferFunds(@ModelAttribute FundTransfer fundTransfer, HttpServletRequest request){

        if (authService.isAuthenticated(request.getCookies(), fundTransfer.getCsrf())){
            return "redirect:/home?status=success";
        }
        return "redirect:/home?status=failed";
    }


}
