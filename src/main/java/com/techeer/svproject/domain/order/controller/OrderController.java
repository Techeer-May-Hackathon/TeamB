package com.techeer.svproject.domain.order.controller;

import com.techeer.svproject.domain.address.Address;
import com.techeer.svproject.domain.address.dto.request.AddressUpdateDto;
import com.techeer.svproject.domain.order.dto.OrderCreateDto;
import com.techeer.svproject.domain.order.dto.OrderMapper;
import com.techeer.svproject.domain.order.dto.OrderResponseDto;
import com.techeer.svproject.domain.order.entity.Order;
import com.techeer.svproject.domain.order.service.OrderService;
import com.techeer.svproject.domain.user.User;
import com.techeer.svproject.domain.user.service.UserService;
import com.techeer.svproject.global.utils.dto.ErrorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.techeer.svproject.global.utils.Constants.API_PREFIX;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_PREFIX+"/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final OrderMapper orderMapper;

    @ResponseBody
    @PostMapping
    public ResponseEntity<OrderResponseDto> save(@RequestBody OrderCreateDto requestDto) {
        User user = userService.findById(requestDto.getUserId());
        Order entity = orderService.save(orderMapper.toEntity(requestDto), user);
        try {
            return new ResponseEntity<>(orderMapper.toResponseDto(entity), HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity(ErrorResponseDto.fromEntity("FORBIDDEN", "주문 생성에 오류가 발생하였습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getDetail(@PathVariable UUID id) {
        Order entity = orderService.findById(id);
        return ResponseEntity
                .ok()
                .body(orderMapper.toResponseDto(entity));
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getByUserId(@RequestParam String userEmail) {

      List<OrderResponseDto> entity = orderService.findAllByEmail(userEmail)
              .stream()
              .map(orderMapper::toResponseDto)
              .collect(Collectors.toList());
      try {
            return new ResponseEntity<>(entity, HttpStatus.ACCEPTED);
        }
        catch(Exception e) {
            return new ResponseEntity(ErrorResponseDto.fromEntity("FORBIDDEN", "주문 목록 조회에 오류가 발생하였습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponseDto> deleteOrder(@PathVariable UUID id) {

        orderService.delete(id);

        return ResponseEntity
                .ok()
                .build();
    }
}
