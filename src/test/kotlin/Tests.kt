import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Projects: DescribeSpec ({
    val subTaskOne = Subtask(time = 8.0,complexity = 1)
    val subTaskTwo = Subtask(time = 5.0, complexity = 2)
    val subTaskTree = Subtask(time = 7.0, complexity = 3)
    val subTaskFour = Subtask(time = 4.0, complexity = 2)
    val taxA = Tax(tax = 3.0)
    val taxB = Tax(tax = 5.0)
    val taskOne = Task(
        subtasks = mutableSetOf(),
        time = 30.0,
        complexity = 3,
        taxes = mutableSetOf(taxB)
    )
    val taskTwo = Task(
        subtasks = mutableSetOf(subTaskOne,subTaskTwo),
        time = 15.0,
        complexity = 1,
        taxes = mutableSetOf(taxA,taxB)
    )
    val taskTree = Task(
        subtasks = mutableSetOf(subTaskOne,subTaskTwo,subTaskTree,subTaskFour),
        time = 25.0,
        complexity = 2,
        taxes = mutableSetOf()
    )
    val project = Project(tasks = mutableSetOf(taskOne,taskTwo,taskTree))

    describe("taskOne"){
        it("The project starts with 0%"){
            project.completed() shouldBe 0.0
        }
        it("Price"){
            taskOne.price() shouldBe 750.0 + 52.5 + 200.0 + 50.125
        }
        it("delay"){
            taskOne.delay() shouldBe 14
        }
        it("complete"){
            taskOne.complete()
            taskOne.filled() shouldBe 100.0
            project.completed() shouldBe (100.0/3)
        }
    }
    describe("taskTwo"){
        it("The project starts with 0%"){
            project.completed() shouldBe 0
            taskTwo.filled() shouldBe 0
        }
        it("price"){
            taskTwo.price() shouldBe 375 + 30
        }
        it("delay"){
            taskTwo.delay() shouldBe 5
        }
        it("incomplete"){
            taskTwo.complete()
            taskTwo.filled() shouldBe (100.0/3)
        }

        describe("SubtaskOne"){
            it("starts with incomplete"){
                subTaskOne.filled() shouldBe false
            }
            it("price"){
                subTaskOne.price() shouldBe 8.0*25
            }
            it("delay"){
                subTaskOne.delay() shouldBe 5
            }
            it("complete"){
                subTaskOne.complete()
                subTaskOne.filled() shouldBe true
            }
        }
        describe("SubTaskTwo"){
            it("starts with incomplete"){
                subTaskTwo.filled() shouldBe false
            }
            it("price"){
                subTaskTwo.price() shouldBe 5.0*25 + 6.25
            }
            it("delay"){
                subTaskTwo.delay() shouldBe 0
            }
            it("complete"){
                subTaskTwo.complete()
                subTaskTwo.filled() shouldBe true
            }
        }
        it("complete"){
            taskTwo.complete()
            taskTwo.filled() shouldBe 100.0
        }
        it("project advance"){
            taskOne.complete()
            taskTwo.complete()
            project.completed() shouldBe (200.0/3)
        }
    }
    describe("TaskTree"){
        it("The project starts with 0%"){
            project.completed() shouldBe 0.0
            taskTree.filled() shouldBe 0.0
        }
        it("Price"){
            taskTree.price() shouldBe 625 + 31.25 + 26.25
        }
        it("incomplete"){
            taskTree.complete()
            taskTree.filled() shouldBe (100.0/5)
        }
        it("complete"){
            subTaskOne.complete()
            subTaskTwo.complete()
            subTaskTree.complete()
            subTaskFour.complete()
            taskTree.complete()
            taskTree.filled() shouldBe (500.0/5)
        }
    }
})