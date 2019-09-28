package app.controller;

import app.message.BookListOrder;
import app.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @PostMapping
    public void postOrder(@AuthenticationPrincipal Principal principal, @Valid @RequestBody BookListOrder order){
        billService.postOrder(principal.getName(),order);
    }
}
