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
    val complexity: Int
    fun complete()
    fun price(): Double {
        if (complexity == 1) {
            return time * 25
        } else if (complexity == 2) {
            return time * 25 + percentage(time * 25, 5.0)
        } else {
            if (time <= 10) {
                return time * 25 + percentage(time * 25, 7.0)
            } else {
                return time * 25 + percentage(time * 25, 7.0) + 10 * (time - 10)
            }
        }

    }

    fun delay(): Int{
        if(complexity == 1){
            return 5
        }
        else if (complexity == 2){
            return percentage(time,10.0).toInt()
        }
        else{
            return percentage(time,20.0).toInt() + 8
        }
    }

    fun percentage(x:Double,y:Double): Double{
        return (x*y)/100
    }
}

class Task(
    val subtasks: MutableSet<Subtask> = mutableSetOf(),
    override val time: Double,
    override val complexity: Int,
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

class Subtask(override val time: Double, override val complexity: Int) : exercise, percentage{
    var filled = false
    fun filled() = this.filled

    override fun complete(){
        filled = true
    }

}