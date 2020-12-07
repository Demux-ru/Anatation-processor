package com.example.processor

import com.example.annotation.AdapterModel
import com.example.annotation.ViewHolderBinding
import com.example.processor.codegeneration.AdapterCodeBuilder
import com.example.processor.models.ModelData
import com.example.processor.models.ViewHolderBindingData
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class Processor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes() =
        mutableSetOf(AdapterModel::class.java.canonicalName)

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {

        val kaptKotlinGeneratedDir =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

        roundEnv.getElementsAnnotatedWith(AdapterModel::class.java).forEach { element ->
                val modelData = getModelData(element)
                val fileName = "${modelData.modelName}Adapter"

                FileSpec.builder(modelData.packageName, fileName)
                    .addType(AdapterCodeBuilder(fileName, modelData).build())
                    .build()
                    .writeTo(File(kaptKotlinGeneratedDir))
            }

        return true
    }

    private fun getModelData(elem: Element): ModelData {
        val viewHolderBindingData = elem.enclosedElements.mapNotNull {
            val viewHolderBinding = it.getAnnotation(ViewHolderBinding::class.java)
            if (viewHolderBinding == null) {
                null
            } else {
                val elementName = it.simpleName.toString()
                val fieldName = elementName
                    .substring(0, elementName.indexOf('$'))
                    .replace("get", "")
                    .toLowerCase()
                ViewHolderBindingData(fieldName, viewHolderBinding.viewId)
            }
        }

        return ModelData(
            packageName = processingEnv.elementUtils.getPackageOf(elem).toString(),
            modelName = elem.simpleName.toString(),
            layoutId = elem.getAnnotation(AdapterModel::class.java).layoutId,
            viewHolderBindingData = viewHolderBindingData
        )
    }
}