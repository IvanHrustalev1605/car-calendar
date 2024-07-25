package com.example.calendarcarsarrivale

import com.example.calendarcarsarrivale.documents.Car
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface CarRepository : MongoRepository<Car, String> {
    fun findAllByCarNumberContaining(carNumber: String) : MutableList<Car>
    fun findAllByExpectedArrive(expectedArriveDate: LocalDate) : MutableList<Car>
    fun findAllByEndedRepair(endedRepair: LocalDate) : MutableList<Car>
}