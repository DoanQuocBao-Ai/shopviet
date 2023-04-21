package com.project.shopviet.Controller;

import com.project.shopviet.DTO.MessageDto;
import com.project.shopviet.Model.Messages;
import com.project.shopviet.Model.OrderItem;
import com.project.shopviet.Model.User;
import com.project.shopviet.Service.MessageService;
import com.project.shopviet.Service.OrderItemService;
import com.project.shopviet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipper")
@CrossOrigin(origins = {"*"})
public class ShipperController {
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    MessageService messageService;

    @GetMapping("/allorder/approved")
    List<OrderItem> getAllOrderApproved(){
        return orderItemService.getAllOrderItemApproved();
    }
    @GetMapping("/allorder/shipped")
    List<OrderItem> getAllOrderShipped(){
        return orderItemService.getAllOrderItemShipped();
    }
    @GetMapping("/allorder/ontheway")
    List<OrderItem> getAllOrderOnTheWay(){
        return orderItemService.getAllOrderItemOnTheWay();
    }
    @GetMapping("/allorder/delivered")
    List<OrderItem> getAllOrderDelivered(){
        return orderItemService.getAllOrderItemDelivered();
    }


    @GetMapping("/order/shipped/{id}")
    OrderItem updateOrderShipped(@PathVariable int id){
        return orderItemService.updateStatusShipped(id);
    }
    @GetMapping("/order/ontheway/{id}")
    OrderItem updateOrderOnTheWay(@PathVariable int id){
        return orderItemService.updateStatusOnTheWay(id);
    }
    @GetMapping("/order/delivered/{id}")
    OrderItem updateOrderDelivered(@PathVariable int id){
        return orderItemService.updateStatusDelivered(id);
    }

    @GetMapping("/profile")
    User getProfileShipper(){
        return userService.getProfile();
    }
    @PostMapping("/message/user/{receiver_id}")
    Messages sendMessage(@RequestBody Messages messages,@PathVariable int receiver_id){
        return messageService.sendMessage(messages,receiver_id);
    }
    @GetMapping("/message/user/{receiver_id}")
    List<MessageDto> getAllMessage(@PathVariable int receiver_id)
    {
        return messageService.findBySenderAndReceiverOrderByCreateAtAsc(receiver_id);
    }
}
