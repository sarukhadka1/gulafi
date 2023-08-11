package com.system.Gulafi.controller;

import com.system.Gulafi.dto.CartDto;
import com.system.Gulafi.entity.Cart;
import com.system.Gulafi.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public String add(CartDto cartDto) {
        cartService.add(cartDto);
        return "redirect:/dashboard/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Cart> carts = cartService.getCartByActiveUser();
        int total = 0;
        for (Cart cart : carts) {
            total += cart.getTotalPrice();
        }
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int  id) {
        cartService.delete(id);
        return "redirect:/cart/list";
    }

    @PostMapping("/edit")
    public String edit(CartDto cartDto) {
        cartService.edit(cartDto);
        return "redirect:/cart/list";
    }


}
