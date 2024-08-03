package edu.uvg.calculadora

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Stack
import kotlin.math.pow
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    // Declaración de los componentes que se usarán en el APP
    private lateinit var txtResult: TextView
    private lateinit var btnDelete: TextView
    private lateinit var btnOpen: TextView
    private lateinit var btnClose: TextView
    private lateinit var btnExponential: TextView
    private lateinit var btnPlus: TextView
    private lateinit var btnSustraction: TextView
    private lateinit var btnEqual: TextView
    private lateinit var btnMult: TextView
    private lateinit var btnDiv: TextView
    private lateinit var btn0: TextView
    private lateinit var btn1: TextView
    private lateinit var btn2: TextView
    private lateinit var btn3: TextView
    private lateinit var btn4: TextView
    private lateinit var btn5: TextView
    private lateinit var btn6: TextView
    private lateinit var btn7: TextView
    private lateinit var btn8: TextView
    private lateinit var btn9: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main) // Después de esta llamada ya puedo usar los elementos
        // Se obtienen los elementos de la UI por medio de su ID
        txtResult = findViewById<TextView>(R.id.txtResult)
        btnDelete = findViewById<TextView>(R.id.btnDelete)
        btnOpen = findViewById<TextView>(R.id.btnOpen)
        btnClose = findViewById<TextView>(R.id.btnClose)
        btnExponential = findViewById<TextView>(R.id.btnExponential)
        btnPlus = findViewById<TextView>(R.id.btnPlus)
        btnSustraction = findViewById<TextView>(R.id.btnSustraction)
        btnEqual = findViewById<TextView>(R.id.btnEqual)
        btnMult = findViewById<TextView>(R.id.btnMult)
        btnDiv = findViewById<TextView>(R.id.btnDiv)
        btn0 = findViewById<TextView>(R.id.btn0)
        btn1 = findViewById<TextView>(R.id.btn1)
        btn2 = findViewById<TextView>(R.id.btn2)
        btn3 = findViewById<TextView>(R.id.btn3)
        btn4 = findViewById<TextView>(R.id.btn4)
        btn5 = findViewById<TextView>(R.id.btn5)
        btn6 = findViewById<TextView>(R.id.btn6)
        btn7 = findViewById<TextView>(R.id.btn7)
        btn8 = findViewById<TextView>(R.id.btn8)
        btn9 = findViewById<TextView>(R.id.btn9)

        // Se hace la llamada al método que establece las acciones de los botones
        actionsButtons()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Metodo que realiza las acciones de los métodos
     *
     */
    private fun actionsButtons(){
        //Se establecen las acciones de cada boton
        btn0.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "0"
        }

        btn1.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "1"
        }

        btn2.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "2"
        }

        btn3.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "3"
        }

        btn4.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "4"
        }

        btn5.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "5"
        }

        btn6.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "6"
        }

        btn7.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "7"
        }

        btn8.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "8"
        }

        btn9.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "9"
        }

        btnDelete.setOnClickListener{
            txtResult.text = ""
        }

        btnOpen.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "("
        }

        btnClose.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + ")"
        }

        btnExponential.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "^"
        }

        btnMult.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "*"
        }

        btnDiv.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "/"
        }

        btnPlus.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "+"
        }

        btnSustraction.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = currentText + "-"
        }

        btnEqual.setOnClickListener{
            var currentText: String = txtResult.text.toString()
            txtResult.text = evaluate(currentText)
        }

    }

    /**
     * Método para determinar la precedencia de un operador.
     *
     * @param c El operador cuya precedencia se desea conocer.
     * @return El nivel de precedencia del operador, o -1 si el operador no es reconocido.
     */
    private fun precedence(c: Char): Int {
        return when (c) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            else -> -1
        }
    }

    /**
     * Convierte una expresión infix a postfix.
     *
     * @param expresion La expresión en formato infix.
     * @return La expresión convertida en formato postfix.
     */
    private fun infixToPostfix(expresion: String): String {
        val result = StringBuilder()
        val stack = Stack<Char>()

        try {
            val cleanedExpression = expresion.replace("\\s+".toRegex(), "")

            var i = 0
            while (i < cleanedExpression.length) {
                val c = cleanedExpression[i]

                if (c.isLetterOrDigit()) {
                    while (i < cleanedExpression.length && cleanedExpression[i].isLetterOrDigit()) {
                        result.append(cleanedExpression[i])
                        i++
                    }
                    result.append(' ')
                    i--
                } else if (c == '(') {
                    stack.push(c)
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(')
                        result.append(stack.pop()).append(' ')
                    if (!stack.isEmpty() && stack.peek() != '(') {
                        return "Expresión inválida"
                    } else {
                        stack.pop()
                    }
                } else {
                    while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek()))
                        result.append(stack.pop()).append(' ')
                    stack.push(c)
                }
                i++
            }

            while (!stack.isEmpty()) {
                if (stack.peek() == '(')
                    return "Expresión inválida"
                result.append(stack.pop()).append(' ')
            }

            return result.toString().trim()
        } catch (e: Exception) {
            return "Expresión inválida"
        }
    }

    /**
     * Método para convertir de string a lista
     */
    private fun stringToList(expression: String): List<String> {
        return expression.split(" ").filter { it.isNotEmpty() }
    }

    /**
     * Método para evaluar la operación postfix
     */
    private fun evaluatePostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()

        for (token in postfix) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                else -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    stack.push(when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> a / b
                        "^" -> a.pow(b)
                        else -> throw IllegalArgumentException("Unknown operator: $token")
                    })
                }
            }
        }
        return stack.pop()
    }

    fun isValidInfixExpression(expression: String): Boolean {
        // Eliminamos espacios para simplificar la validación
        val cleanedExpression = expression.replace("\\s+".toRegex(), "")

        // Expresión regular para verificar números y operadores
        val singleOperatorRegex = Regex("^[+\\-*/^]$")
        val doubleSignRegex = Regex("([+\\-*/^]{2,}|\\d+[+\\-*/^]{2,}|[+\\-*/^]{2,}\\d+)")

        // Verificamos si la expresión contiene solo un símbolo matemático
        if (singleOperatorRegex.matches(cleanedExpression)) {
            return false // Solo un símbolo matemático no es una expresión válida
        }

        // Verificamos si la expresión contiene un número con dos signos consecutivos
        if (doubleSignRegex.containsMatchIn(cleanedExpression)) {
            return false // Contiene números con dos signos consecutivos
        }

        // Si no se encontró ninguna de las condiciones inválidas, la expresión es válida
        return true
    }

    /**
     * Método para evaluar la operación Infix
     */
    private fun evaluate(expresion: String): String{
        if(!isValidInfixExpression(expresion)) return "Error!"
        val modifiedContent = StringBuilder()
        val postfixExpression: String = infixToPostfix(expresion)
        if(postfixExpression.equals("Expresión inválida")) return "Error!"
        val result = evaluatePostfix(stringToList(postfixExpression))
        modifiedContent.append(postfixExpression).append("\n")
        return result.toString()
    }

}