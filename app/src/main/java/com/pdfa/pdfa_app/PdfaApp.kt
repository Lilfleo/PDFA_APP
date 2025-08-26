package com.pdfa.pdfa_app

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.pdfa.pdfa_app.data.repository.CookbookRepository
import dagger.hilt.android.HiltAndroidApp
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PdfaApp : Application() {

    private val TAG = "PdfaApp"
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var cookbookRepository: CookbookRepository

    override fun onCreate() {
        super.onCreate()

        // Créer et ajouter le lifecycle observer
        val lifecycleObserver = AppLifecycleObserver()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)

        Log.d(TAG, "✅ Application créée et lifecycle observer ajouté")
    }

    // Classe interne pour le lifecycle observer
    inner class AppLifecycleObserver : DefaultLifecycleObserver {

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            Log.d(TAG, "🔄 Application passée en arrière-plan - Déplacement des recettes vers History")

            applicationScope.launch {
                try {
                    cookbookRepository.moveRecipesToHistory()
                } catch (e: Exception) {
                    Log.e(TAG, "❌ Erreur lors du déplacement vers History: ${e.message}", e)
                }
            }
        }
    }
}
