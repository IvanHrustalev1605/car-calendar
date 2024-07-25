package com.example.calendarcarsarrivale

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(value = "events", collection = "events")
open class Events {
    @Id var id: String = ""
    open var carNumber: String = ""
    open var arrivedAt: LocalDateTime = LocalDateTime.now()
    open var repairEndAt: LocalDateTime = LocalDateTime.now()
}