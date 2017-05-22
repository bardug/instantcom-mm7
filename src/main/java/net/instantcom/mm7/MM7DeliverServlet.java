package net.instantcom.mm7;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * Created by bardug on 22/5/2017.
 */
public class MM7DeliverServlet extends MM7Servlet {

    private static final long serialVersionUID = 2L;

    public static final String VASP_BEAN_ATTRIBUTE = "net.instantcom.mm7.vasp_bean";
    public static final String VASP_ATTRIBUTE = "net.instantcom.mm7.vasp";

    private VASP vasp;


    @Override
    protected void loadReceiver(ServletContext servletContext) throws ServletException {
        // try to load VASP from Spring context first
        ApplicationContext applicationContext=
                (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (applicationContext != null) {
            String vaspBeanName = (String) servletContext.getAttribute(VASP_BEAN_ATTRIBUTE);
            try {
                vasp = applicationContext.getBean(vaspBeanName, VASP.class);
            } catch (NoSuchBeanDefinitionException e) {
                log("VASP bean wasn't found in Spring context. trying to load from servlet context");
            }
        }

        // in case no VASP is available in Spring, try to load from servlet context
        if (vasp == null) {
            VASP vasp = (VASP) servletContext.getAttribute(VASP_ATTRIBUTE);
            if (vasp == null) {
                throw new ServletException(
                        "please add an instance of a VASP to a servlet context under key net.instantcom.mm7.vasp");
            }
        }
    }

    @Override
    protected MM7Response dispatch(MM7Request req) throws MM7Error {
        if (req instanceof DeliverReq) {
            return vasp.deliver((DeliverReq) req);
        } else {
            throw new MM7Error("method not supported");
        }
    }

    @Override
    protected MM7Context getContext() {
        return vasp.getContext();
    }

    public VASP getVasp() {
        return vasp;
    }
    public void setVasp(VASP vasp) {
        this.vasp = vasp;
    }
}
