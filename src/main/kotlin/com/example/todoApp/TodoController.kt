package com.example.todoApp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/")
class TodoController(@Autowired val coinRepository: CoinRepository) { //TodoControllerクラスの宣言。todoRepositoryという依存性をコンストラクタインジェクションしている。

    @CrossOrigin
    @GetMapping("/api/list")
    fun getCoins(): Array<Coin> {
        return coinRepository.getCoins()
    }

    @PostMapping("/api/list")
    fun saveCoin(@RequestBody coinRequest: CoinRequest): String{
        return coinRepository.saveCoin(coinRequest)
    }

    @DeleteMapping("/api/list/{id}")
    fun deleteCoin(@PathVariable id: Long): String{
        //coinRepository.deleteCoin(id)
        return coinRepository.deleteCoin(id)
    }

//    @GetMapping("/api/list/{ticker}")
//    fun getPrice(): BigDecimal{
//        return coinRepository.getPrice()
//    }
}
