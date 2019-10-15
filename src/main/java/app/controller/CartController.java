package app.controller;

import app.message.BaseResponse;
import app.message.BookOrder;
import app.message.ChangeQuantityInCartItemRequest;
import app.model.Bill;
import app.model.CartItem;
import app.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping
    public BaseResponse getAllItemsInCart(@AuthenticationPrincipal Authentication authentication){
        BaseResponse response = new BaseResponse();
        response.setData(cartService.getAllItemInCart(authentication.getName()));
        return response;
    }
    @PostMapping
    public List<CartItem> addItem(@AuthenticationPrincipal Authentication authentication, @RequestBody BookOrder order){
        return cartService.addItem(authentication.getName(),order);
    }
    @PutMapping("/{id}")
    public BaseResponse editItem(@AuthenticationPrincipal Authentication authentication, @PathVariable Long id, @RequestBody ChangeQuantityInCartItemRequest quantity){
        BaseResponse response = new BaseResponse();
        response.setData(cartService.editItem(authentication.getName(),id,quantity.getQuantity()));
        return response;
    }
    @DeleteMapping("/{id}")
    public BaseResponse deleteItem(@AuthenticationPrincipal Authentication authentication, @PathVariable Long id){
        BaseResponse response = new BaseResponse();
        response.setData(cartService.deleteItem(authentication.getName(),id));
        return response;
    }
    @PostMapping("/pay")
    public BaseResponse makePay(@AuthenticationPrincipal Authentication authentication){
        BaseResponse response = new BaseResponse();
        response.setData(cartService.pay(authentication.getName()));
        return response;

    }
}
