package com.example.reddit_top_publications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddit_top_publications.data.Listing
import com.example.reddit_top_publications.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class MainActivityViewModel : ViewModel() {
    val publicUiState = MutableStateFlow(Listing())

    init {
        getTopPublications()
    }

    fun getTopPublications() {
        viewModelScope.launch {
            publicUiState.update {
                val response = AppContainer().createService(
                    ApiService::class.java,
                    "bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IlNIQTI1NjpzS3dsMnlsV0VtMjVmcXhwTU40cWY4MXE2OWFFdWFyMnpLMUdhVGxjdWNZIiwidHlwIjoiSldUIn0.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNzMxMDY1OTAwLjk5Nzc3NCwiaWF0IjoxNzMwOTc5NTAwLjk5Nzc3NCwianRpIjoidDBsSFY3eVoyRDh2N3hqaXJsQWRoNUNtS21xTEdnIiwiY2lkIjoiOEtqejR2SkM3d2tZdnNYQ2tIcXBRUSIsImxpZCI6InQyXzFjYzdhamF3NHEiLCJhaWQiOiJ0Ml8xY2M3YWphdzRxIiwibGNhIjoxNzMwODE0Nzc2ODkwLCJzY3AiOiJlSnlLVnRKU2lnVUVBQURfX3dOekFTYyIsImZsbyI6OX0.ghEXY1RHfkzwqOpJOVZl8f_DvSRjV7j5HoioXgUBZMXS--BEJT6uzic7yyz7RnVtG-t986epwI5hGto-PQQMFz2_3iVaM0_Asncw0J_g0qdmVigweAxZ4huSKsac3MOUDA1FpxeEH6tcZ1sz4rI1qhdFze4axSwTR1wNPW3uk7Ff0khwdONCN4NLcYKvb1ruuxXEL_TDvmUxVEXPvWDILaz-vTX7RMsKhVj8zplhcC7HgaygCbVtx29XgBRLcAS5rN10gwurG3MnlxOtkg3qtVZLBpm2OEq71S8A16QmLWXRi13XsJCg9Lm88O1b3dK7gB0hL6kAS-76k5MsSy_pzQ"
                ).getTopPublications()
                try {
                    it.copy(data = response.data)
                } catch (e: IOException) {
                    throw e
                } catch (e: HttpException) {
                    throw e
                }
            }
        }
    }
}
