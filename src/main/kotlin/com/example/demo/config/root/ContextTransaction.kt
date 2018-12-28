package com.example.demo.config.root

import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.aop.Advisor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.interceptor.*
import java.lang.Exception
import java.util.*

@Aspect
@Configurable
class ContextTransaction {

	companion object {
		private val log = LoggerFactory.getLogger(ContextTransaction::class.java)
	}

	@Autowired
	lateinit var transactionManager: PlatformTransactionManager

	@Bean
	fun txAdvice(): TransactionInterceptor {
		var source = MatchAlwaysTransactionAttributeSource()
		var transactionAttribute = RuleBasedTransactionAttribute()
		transactionAttribute.setName("*")
		transactionAttribute.rollbackRules = Collections.singletonList(RollbackRuleAttribute(Exception::class.java))
		source.setTransactionAttribute(transactionAttribute)

		return TransactionInterceptor(transactionManager, source)
	}

	@Bean
	fun txAdviceAdvisor(): Advisor {
		val pointcut = AspectJExpressionPointcut()
		pointcut.expression = "execution(* egovframework.let..impl.*Impl.*(..)) or execution(* egovframework.com..*Impl.*(..))"
		return DefaultPointcutAdvisor(pointcut, txAdvice())
	}

	fun transactionAttrubuteSource(): TransactionAttributeSource {
		val source = NameMatchTransactionAttributeSource()
		val readOnlyTx = RuleBasedTransactionAttribute()
		readOnlyTx.isReadOnly = true
		readOnlyTx.propagationBehavior = TransactionDefinition.PROPAGATION_SUPPORTS

		val requiredTx = RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
				Collections.singletonList(RollbackRuleAttribute(Exception::class.java)))
		val txMap = HashMap<String, TransactionAttribute>()

		txMap.put("add*", requiredTx)
		txMap.put("save*", requiredTx)
		txMap.put("insert*", requiredTx)
		txMap.put("update*", requiredTx)
		txMap.put("delete*", requiredTx)
		txMap.put("get*", readOnlyTx)
		txMap.put("query*", readOnlyTx)
		txMap.put("find*", readOnlyTx)
		source.setNameMap(txMap)

		return source
	}

	@Bean(value = ["txInterceptor"])
	fun getTransactionInterceptor(tx: PlatformTransactionManager): TransactionInterceptor {
		return TransactionInterceptor(tx, transactionAttrubuteSource())
	}

	@Bean
	fun pointcutAdvisor(txInterceptor: TransactionInterceptor): AspectJExpressionPointcutAdvisor {
		val pointcutAdvisor = AspectJExpressionPointcutAdvisor()
		pointcutAdvisor.advice = txInterceptor
		pointcutAdvisor.expression = "execution(* egovframework.let..impl.*Impl.*(..)) or execution(* egovframework.com..*Impl.*(..))"
		return pointcutAdvisor
	}
}
