package org.example


class PostfixCalculator {
    fun calculate(postfixExpression: MutableList<String>): Double{
        val stack = mutableListOf<String>()
        val operators = listOf<String>("+", "-", "*", "/", "^", "r")
        var current = ""
        var operand1 = ""
        var operand2 = ""
        while(postfixExpression.isNotEmpty()){
            current = postfixExpression.removeFirst()
            if (current == "e"){
                current = " 2.71828"
            }
            if(current !in operators){
                stack.add(current)
            }else if( current == "r"){
                operand1 = stack.removeLast()
                stack.add(getResult(operand1, operand2, current).toString())
            }else{
                operand1 = stack.removeLast()
                operand2 = stack.removeLast()
                stack.add(getResult(operand1, operand2, current).toString())
            }
        }

        return stack.removeLast().toDouble()
    }

     fun getResult(operand1: String, operand2: String, operator: String): Double{
        return when (operator){
            "+" -> plus(operand1.toDouble(), operand2.toDouble())
            "-" -> minus(operand1.toDouble(), operand2.toDouble())
            "*" -> multiply(operand1.toDouble(), operand2.toDouble())
            "/" -> div(operand1.toDouble(), operand2.toDouble())
            "^" -> power(operand1.toDouble(), operand2.toDouble())
            "r" -> square(operand1.toDouble())
            else -> 0.0

        }
    }
}

fun main(){
    val calculator = PostfixCalculator()
    val list3 = mutableListOf<String>("e", "0", "^", "100", "r", "+", "29", "45", "*", "4", "1", "+", "/", "-")
    println(calculator.calculate(list3))
}