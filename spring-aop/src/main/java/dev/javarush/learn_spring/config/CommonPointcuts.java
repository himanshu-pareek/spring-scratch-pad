package dev.javarush.learn_spring.config;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcuts {

	/**
	 * A join point is in the web layer if the method is defined
	 * in a type in the com.xyz.web package or any sub-package
	 * under that.
	 */
	@Pointcut("within(com.xyz.web..*)")
	public void inWebLayer() {}

	/**
	 * A join point is in the service layer if the method is defined
	 * in a type in the com.xyz.service package or any sub-package
	 * under that.
	 */
	@Pointcut("within(com.xyz.service..*)")
	public void inServiceLayer() {}

	/**
	 * A join point is in the data access layer if the method is defined
	 * in a type in the com.xyz.dao package or any sub-package
	 * under that.
	 */
	@Pointcut("within(com.xyz.dao..*)")
	public void inDataAccessLayer() {}

	/**
	 * A business service is the execution of any method defined on a service
	 * interface. This definition assumes that interfaces are placed in the
	 * "service" package, and that implementation types are in sub-packages.
	 *
	 * If you group service interfaces by functional area (for example,
	 * in packages com.xyz.abc.service and com.xyz.def.service) then
	 * the pointcut expression "execution(* com.xyz..service.*.*(..))"
	 * could be used instead.
	 *
	 * Alternatively, you can write the expression using the 'bean'
	 * PCD, like so "bean(*Service)". (This assumes that you have
	 * named your Spring service beans in a consistent fashion.)
	 */
	@Pointcut("execution(* com.xyz..service.*.*(..))")
	public void businessService() {}

	/**
	 * A data access operation is the execution of any method defined on a
	 * DAO interface. This definition assumes that interfaces are placed in the
	 * "dao" package, and that implementation types are in sub-packages.
	 */
	@Pointcut("execution(* com.xyz.dao.*.*(..))")
	public void dataAccessOperation() {}

}
