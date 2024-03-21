enum class Complexity{
    LOW{
        override fun price(task: exercise): Double = task.time* 25
        override fun delay(task: exercise): Int = 5
    },
    MEDIUM{
        override fun price(task: exercise): Double = task.time * 25 * 1.05
        override fun delay(task: exercise): Int = (task.time * 0.1).toInt()
    },
    High{
        override fun price(task: exercise): Double {
            if(task.time <= 10) return task.time * 25 * 1.07
            else return task.time * 25 * 1.07 + 10*(task.time-10)
        }
        override fun delay(task: exercise): Int = (task.time * 0.2).toInt() + 8
    };
    abstract fun price(task: exercise): Double
    abstract fun delay(task: exercise): Int
}