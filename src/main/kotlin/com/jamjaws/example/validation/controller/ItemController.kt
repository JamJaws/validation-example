package com.jamjaws.example.validation.controller

import com.jamjaws.example.validation.model.Create
import com.jamjaws.example.validation.model.Item
import com.jamjaws.example.validation.model.Update
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.ObjectError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.support.WebExchangeBindException
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/items")
class ItemController {

    @GetMapping
    suspend fun getItems(): List<Item> = listOf(Item(1, "item-1", "item-1"), Item(1, "item-1", "item-1"))

    @PostMapping
    suspend fun createItem(@Validated(Create::class) @RequestBody item: Item): Item = item.copy(id = 1)

    @PutMapping
    suspend fun updateItem(@Validated(Update::class) @RequestBody item: Item): Item = item

}


@ControllerAdvice
class ValidationHandler {
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleException(e: WebExchangeBindException): ResponseEntity<List<String?>> {
        val errors = e.bindingResult
            .allErrors
            .stream()
            .map { obj: ObjectError -> obj.objectName + ": " + obj.defaultMessage }
            .collect(Collectors.toList())
        return ResponseEntity.badRequest().body(errors)
    }
}
