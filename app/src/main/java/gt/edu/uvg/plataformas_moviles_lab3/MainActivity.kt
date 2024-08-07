package gt.edu.uvg.plataformas_moviles_lab3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.example.ExpressionConverter
import org.example.ExpressionValidator
import org.example.PostfixCalculator

class MainActivity : AppCompatActivity() {

    val textViewOperation: TextView = findViewById(R.id.textView_operation)
    val textViewRestult: TextView = findViewById(R.id.textView_restult)
    val operators = listOf<String>("+", "-", "*", "/", "^", "r")
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



        val button_ac: Button = findViewById(R.id.button_ac)
        button_ac.setOnClickListener{textViewOperation.text = ""}

        val button_e: Button = findViewById(R.id.button_e)
        button_e.setOnClickListener{editOperation(textViewOperation, "e")}

        val button_delete: Button = findViewById(R.id.button_delete)
        button_delete.setOnClickListener{deleteLastCharacter(textViewOperation)}

        val button_open_p: Button = findViewById(R.id.button_open_p)
        button_open_p.setOnClickListener{editOperation(textViewOperation, "(")}

        val button_div: Button = findViewById(R.id.button_div)
        button_div.setOnClickListener{editOperation(textViewOperation, "/")}

        val button_multiply: Button = findViewById(R.id.button_multiply)
        button_multiply.setOnClickListener{editOperation(textViewOperation, "*")}

        val button_closing_p: Button = findViewById(R.id.button_closing_p)
        button_closing_p.setOnClickListener{editOperation(textViewOperation, ")")}

        val button_7: Button = findViewById(R.id.button_7)
        button_7.setOnClickListener{editOperation(textViewOperation, "7")}

        val button_8: Button = findViewById(R.id.button_8)
        button_8.setOnClickListener{editOperation(textViewOperation, "8")}

        val button_9: Button = findViewById(R.id.button_9)
        button_9.setOnClickListener{editOperation(textViewOperation, "9")}

        val button_minus: Button = findViewById(R.id.button_minus)
        button_minus.setOnClickListener{editOperation(textViewOperation, "-")}

        val button_4: Button = findViewById(R.id.button_4)
        button_4.setOnClickListener{editOperation(textViewOperation, "4")}

        val button_5: Button = findViewById(R.id.button_5)
        button_5.setOnClickListener{editOperation(textViewOperation, "5")}

        val button_6: Button = findViewById(R.id.button_6)
        button_6.setOnClickListener{editOperation(textViewOperation, "6")}

        val button_plus: Button = findViewById(R.id.button_plus)
        button_plus.setOnClickListener{editOperation(textViewOperation, "+")}

        val button_1: Button = findViewById(R.id.button_1)
        button_1.setOnClickListener{editOperation(textViewOperation, "1")}

        val button_2: Button = findViewById(R.id.button_2)
        button_2.setOnClickListener{editOperation(textViewOperation, "2")}

        val button_3: Button = findViewById(R.id.button_3)
        button_3.setOnClickListener{editOperation(textViewOperation, "3")}

        val button_sqrt: Button = findViewById(R.id.button_sqrt)
        button_9.setOnClickListener{editOperation(textViewOperation, "r")}

        val button_period: Button = findViewById(R.id.button_period)
        button_e.setOnClickListener{editOperation(textViewOperation, ".")}

        val button_0: Button = findViewById(R.id.button_0)
        button_0.setOnClickListener{editOperation(textViewOperation, "0")}

        val button_elevate: Button = findViewById(R.id.button_elevate)
        button_elevate.setOnClickListener{editOperation(textViewOperation, "^")}

        val button_equals: Button = findViewById(R.id.button_equals)
        button_equals.setOnClickListener{showResult(textViewOperation, textViewRestult)}
    }

    fun editOperation(textView: TextView, str: String){
        if(previous == str && str in operators){

        }else{
            textView.text = textView.text.toString() + str
        }

    }

    fun deleteLastCharacter(textView: TextView){
        var text = textView.text.toString()
        text = text.substring(0, text.length -1)
        textView.text = text
    }

    fun showResult(textView: TextView, resultTextView: TextView){
        var operation = textView.text.toString()
        var result = operate(operation)


        textView.text = ""
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