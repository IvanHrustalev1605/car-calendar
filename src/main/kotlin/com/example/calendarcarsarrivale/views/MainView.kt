package com.example.calendarcarsarrivale

import com.example.calendarcarsarrivale.service.CarDocumentService
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.datepicker.DatePicker
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.ItemClickEvent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import java.time.LocalDate

@Route
class MainView(private val carDocumentService: CarDocumentService) : VerticalLayout() {
    data class Event(val date: LocalDate, val description: String)
    data class Car(val number:String)

    private val gridCars = Grid(com.example.calendarcarsarrivale.documents.Car::class.java)
    private val cars = carDocumentService.all()
    init {
        gridCars.isVisible = false
        val datePicker = DatePicker("Выберите дату")
        datePicker.addValueChangeListener {
            val value: LocalDate = it.value
            val cars = carDocumentService.showAllArriveByDay(value)
            gridCars.isVisible = true
            gridCars.setItems(cars)
        }
        val comboBox = ComboBox<String>()

        comboBox.isVisible = true
        comboBox.label = "Выберите машину"
        comboBox.setItems(cars.map { it.carNumber }.toList())
    gridCars.addItemClickListener {carItem ->
        val dialog = createModalWindow(carItem)
        add(dialog)
        dialog.isVisible = true
        dialog.headerTitle = "Укажите количество дней ремонта"
        dialog.open()
        }

        add(datePicker, comboBox, gridCars)
    }
    private fun refreshCarGrid() {
        gridCars.setItems(carDocumentService.all())
    }
    private fun createModalWindow(carItem: ItemClickEvent<com.example.calendarcarsarrivale.documents.Car>) : Dialog {
        val dialog = Dialog()
        dialog.isVisible = false
        val textField = TextField()
        textField.isVisible = true
        val updateCarRepairButton = Button("Сохранить")
        updateCarRepairButton.addClickListener {
            carItem.item.endedRepair = LocalDate.now().plusDays(textField.value.toLong())
            carDocumentService.save(carItem.item)
            dialog.close()
            refreshCarGrid()
        }
        dialog.add(textField, updateCarRepairButton)
        dialog.isModal = true
        return dialog;
    }
}