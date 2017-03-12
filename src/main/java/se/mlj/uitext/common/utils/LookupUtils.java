package se.mlj.uitext.common.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

public final class LookupUtils {
	public enum JNDILookupNameSpaces {
	    JAVA_GLOBAL("java:global%s/%s/%s%s") {

	      @Override
	      public String format(final String applicationName, final String moduleName, 
	                           final String beanName, final String fullyQualifiedInterfaceName) {
	        final String appName = applicationName != null ? "/" + applicationName : "";
	        final String interfaceName = fullyQualifiedInterfaceName != null ? "!" + fullyQualifiedInterfaceName : "";
	        return String.format(getJndiFormat(), appName, moduleName, beanName, interfaceName);
	            }
	        },

	    JAVA_APP("java:app/%s/%s%s") {

	      @Override
	      public String format(final String applicationName, final String moduleName, 
	                           final String beanName, final String fullyQualifiedInterfaceName) {
	        final String interfaceName = fullyQualifiedInterfaceName != null ? "!" + fullyQualifiedInterfaceName : "";
	        return String.format(getJndiFormat(), moduleName, beanName, interfaceName);
	      }
	    },

	    JAVA_MODULE("java:module/%s%s") {

	      @Override
	      public String format(final String applicationName, final String moduleName, final String beanName, final String fullyQualifiedInterfaceName) {
	        final String interfaceName = fullyQualifiedInterfaceName != null ? "!" + fullyQualifiedInterfaceName : "";
	        return String.format(getJndiFormat(), beanName, interfaceName);
	      }
	    };

	    private final String jndiFormat;

	    private JNDILookupNameSpaces(final String jndiFormat) {
	      this.jndiFormat = jndiFormat;
	    }

	    protected String getJndiFormat() {
	      return jndiFormat;
	    }

	    public abstract String format(final String applicationName, final String moduleName, 
	                                  final String beanName, final String fullyQualifiedInterfaceName);
	  }

	  private static final String USER_TRANSACTION_LOOKUP_NAME = "java:comp/UserTransaction";

	  private static final String MODULE_NAME_LOOKUP_PROPERTY = "java:module/ModuleName";

	  private LookupUtils() {
	  }

	  public static UserTransaction lookupUT() {
	    try {
	      final InitialContext ic = new InitialContext();
	      return (UserTransaction) ic.lookup(USER_TRANSACTION_LOOKUP_NAME);
	    } catch (NamingException e) {
	      throw new RuntimeException(e.getMessage(), e);
	    }
	  }

	  public static <T> T lookupWithinApp(final String beanName, 
	                                   final String fullyQualifiedInterfaceName) {

	    final String module = genericLookup(MODULE_NAME_LOOKUP_PROPERTY);

	    if (module != null) {
	      final String jndiLookupName = JNDILookupNameSpaces.JAVA_APP.format(null, module, beanName, fullyQualifiedInterfaceName);

	      return genericLookup(jndiLookupName);
	    } else {
	      return lookupWithinModule(beanName, fullyQualifiedInterfaceName);
	    }
	  }

	  public static <T> T lookupWithinModule(final String beanName, 
	                                      final String fullyQualifiedInterfaceName) {
	    final String jndiLookupName = JNDILookupNameSpaces.JAVA_MODULE.format(null, null, beanName, fullyQualifiedInterfaceName);
	    return genericLookup(jndiLookupName);
	  }

	  @SuppressWarnings("unchecked")
	  private static <T> T genericLookup(final String jndiName) {
	    try {
	    	System.out.println(jndiName);
	      final InitialContext context = new InitialContext();
	      return (T) context.lookup(jndiName);
	    } catch (NamingException e) {
	      throw new RuntimeException(e);
	    }
	  }
}
