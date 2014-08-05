package com.studerw.sso.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/ajax")
public class AjaxController {
    private static final Logger log = Logger.getLogger(AjaxController.class);

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PojoResponse> get(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date now = new Date();
        String message = "PONG @ " + fmt.format(now);
        PojoResponse pojoResponse = new PojoResponse(message);
        return new ResponseEntity<PojoResponse>(pojoResponse, HttpStatus.OK);
    }

    public static class PojoResponse{
        String message;

        public PojoResponse(String message){
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
