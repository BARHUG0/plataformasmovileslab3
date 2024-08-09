package gt.edu.uvg.plataformas_moviles_lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    lateinit var textViewOperation: TextView
    lateinit var textViewRestult: TextView


    val two_operands_operators = listOf<String>("+", "-", "*", "/", "^")
    val one_operand_operators = listOf<String>("√")
    var previous = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }

        val textViewOperation: TextView = findViewById(R.id.textView_operation)
        val textViewResult: TextView = findViewById(R.id.textView_restult)


        val button_ac: Button = findViewById(R.id.button_ac)
        button_ac.setOnClickListener{textViewOperation.text = ""; textViewResult.text = ""; previous = ""}

        val button_e: Button = findViewById(R.id.button_e)
        button_e.setOnClickListener{editOperation(textViewOperation, "e")}

        val button_delete: Button = findViewById(R.id.button_delete)
        button_delete.setOnClickListener{deleteLastCharacter(textViewOperation)}

        val button_open_p: Button = findViewById(R.id.button_open_p)
        button_open_p.setOnClickListener{editOperation(textViewOperation, " (")}

        val button_div: Button = findViewById(R.id.button_div)
        button_div.setOnClickListener{editOperation(textViewOperation, " / ")}

        val button_multiply: Button = findViewById(R.id.button_multiply)
        button_multiply.setOnClickListener{editOperation(textViewOperation, " * ")}

        val button_closing_p: Button = findViewById(R.id.button_closing_p)
        button_closing_p.setOnClickListener{editOperation(textViewOperation, ") ")}

        val button_7: Button = findViewById(R.id.button_7)
        button_7.setOnClickListener{editOperation(textViewOperation, "7")}

        val button_8: Button = findViewById(R.id.button_8)
        button_8.setOnClickListener{editOperation(textViewOperation, "8")}

        val button_9: Button = findViewById(R.id.button_9)
        button_9.setOnClickListener{editOperation(textViewOperation, "9")}

        val button_minus: Button = findViewById(R.id.button_minus)
        button_minus.setOnClickListener{editOperation(textViewOperation, " - ")}

        val button_4: Button = findViewById(R.id.button_4)
        button_4.setOnClickListener{editOperation(textViewOperation, "4")}

        val button_5: Button = findViewById(R.id.button_5)
        button_5.setOnClickListener{editOperation(textViewOperation, "5")}

        val button_6: Button = findViewById(R.id.button_6)
        button_6.setOnClickListener{editOperation(textViewOperation, "6")}

        val button_plus: Button = findViewById(R.id.button_plus)
        button_plus.setOnClickListener{editOperation(textViewOperation, " + ")}

        val button_1: Button = findViewById(R.id.button_1)
        button_1.setOnClickListener{editOperation(textViewOperation, "1")}

        val button_2: Button = findViewById(R.id.button_2)
        button_2.setOnClickListener{editOperation(textViewOperation, "2")}

        val button_3: Button = findViewById(R.id.button_3)
        button_3.setOnClickListener{editOperation(textViewOperation, "3")}

        val button_sqrt: Button = findViewById(R.id.button_sqrt)
        button_sqrt.setOnClickListener{editOperation(textViewOperation, "√ ")}

        val button_period: Button = findViewById(R.id.button_period)
        button_period.setOnClickListener{editOperation(textViewOperation, ".")}

        val button_0: Button = findViewById(R.id.button_0)
        button_0.setOnClickListener{editOperation(textViewOperation, "0")}

        val button_elevate: Button = findViewById(R.id.button_elevate)
        button_elevate.setOnClickListener{editOperation(textViewOperation, " ^ ")}

        val button_equals: Button = findViewById(R.id.button_equals)
        button_equals.setOnClickListener{showResult(textViewOperation, textViewResult)}
    }

    fun editOperation(textView: TextView, str: String){
        if(((previous.trim() in two_operands_operators || previous == "") && str.trim() in two_operands_operators) || ((previous.trim() in one_operand_operators) && str.trim() in one_operand_operators)){
            var toast = Toast.makeText(this, "Los operadores deben ser precedidos por un numero",Toast.LENGTH_SHORT)
            toast.show()
        }else if(previous == "ERASE" && str.trim() !in two_operands_operators){
            textView.text = str
        }else if(str.trim() in one_operand_operators){
            if (previous == "" || previous.trim() == "("){
                textView.text = textView.text.toString() + str
            }
            else if(previous.trim() in two_operands_operators){
                textView.text = textView.text.toString() + str
            }else{
                textView.text = textView.text.toString() + " * " + str
            }
        }else{
            textView.text = textView.text.toString() + str

        }
        previous = str

    }

    fun deleteLastCharacter(textView: TextView){
        var text = textView.text.toString()
        if(text != ""){
            text = text.substring(0, text.length -1)
            textView.text = text
        }

    }

    fun showResult(textView: TextView, resultTextView: TextView){

        var operation = textView.text.toString()
        var result = ""
        if(operation == ""){
            var toast = Toast.makeText(this, "Ingrese una operacion",Toast.LENGTH_SHORT)
            toast.show()
        }else{
            result = operate(operation)
        }


        if (result == "ERROR" || result == "Infinity"){
            textView.text = ""
            previous = ""
        }else{
            textView.text = result
            previous = "ERASE"
        }

        resultTextView.text = result
    }

    fun operate(operation: String): String{
        var validator = ExpressionValidator();
        if(!validator.validateCharacters(operation)){
            var toast = Toast.makeText(this, "Caracter Invalido",Toast.LENGTH_SHORT)
            toast.show()
            return "ERROR"
        }
        if(!validator.validateParenthesis(operation)){
            var toast = Toast.makeText(this, "Los parentesis no estan balanceados",Toast.LENGTH_SHORT)
            toast.show()
            return "ERROR"
        }

        var converter = ExpressionConverter()
        var postfix = converter.infixToPostfix(operation)

        var calculator = PostfixCalculator()
        return calculator.calculate(postfix).toString()

    }

}


class ExpressionConverter {
    fun infixToPostfix(expression: String): MutableList<String> {
        val stack = mutableListOf<Char>();
        val postfix = mutableListOf<Char>();
        val numbersOrSpace =
            listOf<Char>('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'e', ' ', '.')


        for (char in expression) {
            when (char) {
                in numbersOrSpace -> postfix.add(char)
                '(' -> stack.add(char)
                '√' -> stack.add(char)
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

        while (stack.isNotEmpty()) {
            postfix.add(stack.removeLast())

        }

        return getPostFixList(postfix.joinToString(""));
    }

    private fun getPostFixList(postfix: String): MutableList<String> {
        val operators = listOf<Char>('+', '-', '*', '/', '^', '√')
        val postfixList = mutableListOf<String>()
        var current = ""
        for (char in postfix) {
            if (char in operators) {
                if (current != "") {
                    postfixList.add(current)
                    current = ""
                }
                postfixList.add(char.toString())
            } else if (char != ' ') {
                current = current + char
            } else if (current != "") {
                postfixList.add(current)
                current = ""
            }


        }
        if (postfixList.isEmpty()){
            postfixList.add(current)
        }
        return postfixList;
    }

    private fun getPrecedence(char: Char): Int {
        return when (char) {
            '+' -> 1;
            '-' -> 1;
            '*' -> 2;
            '/' -> 2;
            '^' -> 3;
            '√' -> 3;
            else -> 0;
        }
    }
}


class ExpressionValidator {
    fun validateCharacters(expression: String): Boolean {
        val regex = Regex("[^\\d +\\-/*^e√()\\[\\].]")
        return !regex.containsMatchIn(expression);
    }

    fun validateParenthesis(expression: String): Boolean {
        val stack = mutableListOf<Char>();
        for (ch in expression) {
            if (ch == '(') {
                stack.add(ch);
            } else if (ch == ')')
                if (stack.isNotEmpty()) {
                    stack.removeLast()
                } else {
                    return false;
                }
        }

        return stack.isEmpty();
    }
}


class PostfixCalculator {
    fun calculate(postfixExpression: MutableList<String>): Double {

        val stack = mutableListOf<String>()
        val operators = listOf<String>("+", "-", "*", "/", "^", "√")
        var current = ""
        var operand1 = ""
        var operand2 = ""
        while (postfixExpression.isNotEmpty()) {
            current = postfixExpression.removeFirst()
            if (current == "e") {
                current = " " + Math.E.toString()
            }
            if (current !in operators) {
                stack.add(current)
            } else if (current == "√") {
                operand1 = stack.removeLast()
                stack.add(getResult(operand1, operand2, current).toString())
            } else {
                operand1 = stack.removeLast()
                operand2 = stack.removeLast()
                stack.add(getResult(operand1, operand2, current).toString())
            }
        }
        if(stack.isEmpty()){
            return current.toDouble()
        }else{
            return stack.removeLast().toDouble()
        }

    }

    fun getResult(operand1: String, operand2: String, operator: String): Double {
        return when (operator) {
            "+" -> operand1.toDouble().plus(operand2.toDouble())
            "-" -> operand2.toDouble().minus(operand1.toDouble())
            "*" -> operand1.toDouble() * operand2.toDouble()
            "/" -> operand2.toDouble().div(operand1.toDouble())
            "^" -> operand2.toDouble().pow(operand1.toDouble())
            "√" -> sqrt(operand1.toDouble())
            else -> 0.0

        }
    }
}

