package ch.napohaku.playground.api.security;

import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserInfoController {
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> currentUserName(Principal principal) {
        User user =  new User(SecurityContextHolder.getContext().getAuthentication());

        user.add(linkTo(methodOn(UserInfoController.class).currentUserName(principal)).withSelfRel());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}