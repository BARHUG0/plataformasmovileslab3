package org.example

class ExpressionConverter {
    fun infixToPostfix( expression: String): MutableList<String> {
        val stack = mutableListOf<Char>();
        val postfix = mutableListOf<Char>();
        val numbersOrSpace = listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'e',' ')


        for (char in expression) {
            when (char) {
                in numbersOrSpace -> postfix.add(char)
                '(' -> stack.add(char)
                'r' -> stack.add(char)
                '^' -> stack.add(char)
                ')' -> {
                    while (stack.isNotEmpty() && stack.last() != '(') {
                        postfix.add(stack.removeLast())
                    }
                    stack.removeLast()
                }

                else -> {
                    while (stack.isNotEmpty() && getPrecedence(char) <= getPrecedence(stack.last())) {
                        postfix.add(stack.removeLast())

                    }
                    stack.add(char)
                }
            }

        }

        while (stack.isNotEmpty()){
            postfix.add(stack.removeLast())

        }

        return getPostFixList(postfix.joinToString("")) ;
    }

    private fun getPostFixList(postfix: String): MutableList<String> {
        val operators = listOf<Char>('+', '-', '*', '/', '^', 'r' )
        val postfixList = mutableListOf<String>()
        var current = ""
        for (char in postfix) {
            if(char in operators) {
                if(current != "") {
                    postfixList.add(current)
                    current = ""
                }
                postfixList.add(char.toString())
            }else if(char != ' '){
                current = current + char
            }else if(current != ""){
                postfixList.add(current)
                current=""
            }


        }
        return postfixList;
    }

    private fun getPrecedence(char: Char): Int{
        return when (char) {
            '+' -> 1;
            '-' -> 1;
            '*' -> 2;
            '/' -> 2;
            '^' -> 3;
            'r' -> 3;
            else -> 0;
        }
    }
}

//fun main(){
//    val expressionConverter = ExpressionConverter()
//    val postfixTest = expressionConverter.infixToPostfix("e ^ 2 + r 10 - 29 * 45 / (8 +99)")
//    println(postfixTest)
//}


