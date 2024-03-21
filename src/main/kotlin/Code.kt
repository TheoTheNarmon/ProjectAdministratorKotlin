class Project(val tasks: MutableSet<Task> = mutableSetOf()): percentage {
    fun completed(): Double {
        if(tasks.size == 0){
            return 0.0
        }
        else {
            return average(
                this.tasks.size.toDouble(),
                this.tasks.filter { task -> task.filled() == 100.0 }.size.toDouble()
            )
        }
    }



}
class Tax(val tax: Double){
    fun tax() = this.tax
}

interface percentage{
    fun average(x:Double,y:Double): Double{
        return (y*100)/x
    }
}

interface exercise{
    val time: Double
    val complexity: Complexity
    fun complete()
    fun price(): Double = complexity.price(this)

    fun delay(): Int = complexity.delay(this)

    fun percentage(x:Double,y:Double): Double{
        return (x*y)/100
    }
}

class Task(
    val subtasks: MutableSet<Subtask> = mutableSetOf(),
    override val time: Double,
    override val complexity: Complexity,
    val taxes: MutableSet<Tax> = mutableSetOf()
    ): exercise, percentage{
    var filled: Double = 0.0
    fun filled() = this.filled

    override fun price(): Double{
        if (subtasks.size >= 3){
            return super.price() + percentage(super.price(),4.0) + percentage(super.price(),taxes())
        }
        else return super.price() + percentage(super.price(),taxes())
    }

    fun taxes(): Double {
        return taxes.sumOf {tax -> tax.tax()}
    }

    override fun complete(){
        if (subtasks.isEmpty()){
            filled = 100.0
        }
        else{
            filled = average(
                (this.subtasks.size + 1).toDouble(),
                (this.subtasks.filter{ subtask -> subtask.filled() }.size + 1).toDouble()
            )
        }
    }
}

class Subtask(override val time: Double, override val complexity: Complexity) : exercise, percentage{
    var filled = false
    fun filled() = this.filled

    override fun complete(){
        filled = true
    }

}