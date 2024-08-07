package org.example

class ExpressionValidator {
    fun validateCharacters(expression: String): Boolean{
        val regex = Regex("[^\\d +\\-/*^er()\\[\\]]")
        return !regex.containsMatchIn(expression);
    }

    fun validateParenthesis(expression: String): Boolean{
        val stack = mutableListOf<Char>();
        for (ch in expression) {
            if (ch == '(') {
                stack.add(ch);
            }
            else if(ch == ')')
                if (stack.isNotEmpty()){
                    stack.removeLast()
                }else{
                    return false;
                }
        }

        return stack.isEmpty();
    }
}
