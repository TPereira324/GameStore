package pt.iade.ei.gamestore.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.iade.ei.gamestore.R
import pt.iade.ei.gamestore.ui.theme.GameStoreTheme

// RegisterActivity.kt
class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameStoreTheme {
                RegisterScreen(
                    onRegisterSuccess = {
                        // Voltar para Login após registro bem-sucedido
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.putExtra("REGISTER_SUCCESS", true)
                        startActivity(intent)
                        finish()
                    },
                    onNavigateToLogin = {
                        // Voltar para Login
                        finish()
                    }
                )
            }
        }
    }
}

// RegisterScreen.kt
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Icon(
            painter = painterResource(id = R.mipmap.ic_launcher_foreground),
            contentDescription = "Game Store Logo",
            modifier = Modifier.size(200.dp),
            tint = Color(0xFF2563EB)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Criar conta",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Junte-se à nossa comunidade",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nome") },
            placeholder = { Text("Seu nome completo") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Nome") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.value != null
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            label = { Text("Telefone") },
            placeholder = { Text("+351 912 345 678") },
            leadingIcon = { Icon(Icons.Outlined.Phone, contentDescription = "Telefone") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = errorMessage.value != null
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo de Email
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            placeholder = { Text("seu@email.com") },
            leadingIcon = {
                Icon(Icons.Filled.Email, contentDescription = "Email")
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errorMessage.value != null
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Campo de Senha
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Senha") },
            placeholder = { Text("Mínimo 6 caracteres") },
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Senha")
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = errorMessage.value != null
        )

        // Mensagem de erro
        if (errorMessage.value != null) {
            Text(
                text = errorMessage.value!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão de Registro
        Button(
            onClick = {
                if (validateRegister(name.value, phone.value, email.value, password.value)) {
                    isLoading.value = true
                    errorMessage.value = null

                    simulateRegister(name.value, phone.value, email.value, password.value) { success, message ->
                        isLoading.value = false
                        if (success) {
                            onRegisterSuccess()
                        } else {
                            errorMessage.value = message
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading.value
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Cadastrar", style = MaterialTheme.typography.labelLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Link para Login
        TextButton(onClick = onNavigateToLogin) {
            Text("Já tem uma conta? Entre aqui")
        }
    }
}

// Funções de validação para registro
private fun validateRegister(name: String, phone: String, email: String, password: String): Boolean {
    return name.isNotBlank() &&
            android.util.Patterns.PHONE.matcher(phone).matches() &&
            email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
            password.length >= 6
}

// Simulação de registro
private fun simulateRegister(
    name: String,
    phone: String,
    email: String,
    password: String,
    callback: (Boolean, String) -> Unit
) {
    Handler(Looper.getMainLooper()).postDelayed({
        // Simular verificação se email já existe
        val existingEmails = listOf("existing@email.com", "test@test.com")

        if (existingEmails.contains(email)) {
            callback(false, "Este email já está em uso")
        } else {
            callback(true, "Registro bem-sucedido")
        }
    }, 2000)
}
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    GameStoreTheme {
        RegisterScreen(
            onRegisterSuccess = { },
            onNavigateToLogin = { }
        )
    }
}