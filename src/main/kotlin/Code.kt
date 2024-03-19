class Project(val tareas: MutableSet<Task> = mutableSetOf()) {

}
class Tax(val tax: Double){
    fun tax() = this.tax
}


interface exercise{
    val time: Double
    val complexity: Int
    fun complete()
    fun price(): Double {
        if (complexity == 1){
            return time*25
        }
        else if (complexity == 2){
            return time*25 + percentage(time*25,5.0)
        }
        else{
            if (time >= 10){
                return time*25 + percentage(time*25,7.0)
            }
            else{
                return time*25 + percentage(time*25,7.0) + 10*(time-10)
            }
        }

    }
    fun percentage(x:Double,y:Double): Double{
        return (x*y)/100
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
}

class Task(
    val subtasks: MutableSet<Subtask> = mutableSetOf(),
    override val time: Double,
    override val complexity: Int,
    val taxes: MutableSet<Tax> = mutableSetOf()
    ): exercise{
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
    fun all(): Int{
        var i = 0
        for (item in subtasks){
            if (item.filled() == true){
                i = i + 1
            }
        }
        return i
    }

    override fun complete(){
        if (subtasks.isEmpty()){
            filled = 100.0
        }
        else{
            filled = percentage(
                (this.subtasks.size + 1).toDouble(),
                (this.subtasks.filter{ subtask -> subtask.filled() }.size + 1).toDouble()
            )
        }
    }
}

class Subtask(override val time: Double, override val complexity: Int) : exercise{
    var filled = false
    fun filled() = this.filled

    override fun complete(){
        filled = true
    }

}