class Project(val tareas: MutableSet<Task> = mutableSetOf()) {

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
}

class Task(val subtasks: MutableSet<Subtask> = mutableSetOf()): exercise{
    var filled: Double = 0.0
    fun filled() = this.filled

    override fun price(): Double{
        if (subtasks.size >= 3){
            return super.price() + percentage(super.price(),4.0)
        }
        else return super.price()
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

class Subtask() : exercise{
    var filled = false
    fun filled() = this.filled

    override fun complete(){
        filled = true
    }

}