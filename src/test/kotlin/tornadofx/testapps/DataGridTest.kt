package tornadofx.testapps

import javafx.collections.FXCollections
import javafx.scene.control.SelectionMode
import javafx.scene.input.KeyCombination
import tornadofx.*
import tornadofx.testapps.DataGridTestApp.Companion.images

class DataGridTestApp : App(DataGridTest::class, DataGridStyles::class) {
    companion object {
        val images = mapOf(
                "kittens" to listOf(
                        "http://i.imgur.com/DuFZ6PQb.jpg",
                        "http://i.imgur.com/o2QoeNnb.jpg",
                        "http://i.imgur.com/P8wpBvub.jpg",
                        "http://i.imgur.com/a2NDDglb.jpg",
                        "http://i.imgur.com/79C37Scb.jpg",
                        "http://i.imgur.com/N0fPCU2b.jpg",
                        "http://i.imgur.com/qRfWC9wb.jpg",
                        "http://i.imgur.com/i3crLYcb.jpg"),

                "puppies" to listOf(
                        "http://i.imgur.com/37hCL2Pb.jpg",
                        "http://i.imgur.com/v9vBE67b.jpg",
                        "http://i.imgur.com/koQoEExb.jpg",
                        "http://i.imgur.com/qRfWC9wb.jpg",
                        "http://i.imgur.com/ftDV8oJb.jpg")

        )
    }
}

class DataGridTest : View("DataGrid") {
    var datagrid: DataGrid<String> by singleAssign()

    override val root = borderpane {
        left {
            vbox {
                combobox(values = images.keys.toList()) {
                    promptText = "Select images"
                    valueProperty().onChange {
                        datagrid.items = FXCollections.observableArrayList(images[it])
                    }
                    accelerators[KeyCombination.valueOf("k")] = { value = "kittens" }
                    accelerators[KeyCombination.valueOf("p")] = { value = "puppies" }
                }
                button("Add") {
                    action {
                        datagrid.items.add("http://i.imgur.com/bvqTBT0b.jpg")
                    }
                }
            }
        }
        center {
            datagrid = datagrid<String> {
                setPrefSize(550.0, 550.0)

                selectionModel.selectionMode = SelectionMode.MULTIPLE
                maxCellsInRow = 3
                //maxRows = 3
                cellWidth = 164.0
                cellHeight = 164.0

                cellCache {
                    imageview(it, true)
                }

                onUserSelect(1) {
                    println("Selected $selectedItem")
                }
            }
        }
        bottom {
            label(stringBinding(datagrid.selectionModel.selectedItems) { joinToString(", ") })
        }
    }

}

class DataGridStyles : Stylesheet() {
    init {
        datagridCell and selected {
            opacity = 0.7
        }
    }
}