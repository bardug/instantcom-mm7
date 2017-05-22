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
public class MM7SubmitServlet extends MM7Servlet {
    private static final long serialVersionUID = 3L;

    public static final String MMSC_BEAN_ATTRIBUTE = "net.instantcom.mm7.mmsc_bean";
    public static final String MMSC_ATTRIBUTE = "net.instantcom.mm7.mmsc";

    private MMSCServer mmsc;

    @Override
    protected void loadReceiver(ServletContext servletContext) throws ServletException {
        // try to load MMSC from Spring context first
        ApplicationContext applicationContext=
                (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        if (applicationContext != null) {
            String mmscBeanName = (String) servletContext.getAttribute(MMSC_BEAN_ATTRIBUTE);
            try {
                mmsc = applicationContext.getBean(mmscBeanName, MMSCServer.class);
            } catch (NoSuchBeanDefinitionException e) {
                log("MMSC bean wasn't found in Spring context. trying to load from servlet context");
            }
        }

        // in case no MMSC is available in Spring, try to load from servlet context
        if (mmsc == null) {
            MMSCServer mmsc = (MMSCServer) servletContext.getAttribute(MMSC_ATTRIBUTE);
            if (mmsc == null) {
                throw new ServletException(
                        "please add an instance of a MMSC to a servlet context under key net.instantcom.mm7.mmsc");
            }
        }    }

    @Override
    protected MM7Response dispatch(MM7Request req) throws MM7Error {
        if (req instanceof SubmitReq) {
            return mmsc.receive((SubmitReq) req);
        } else {
            throw new MM7Error("method not supported");
        }
    }

    @Override
    protected MM7Context getContext() {
        return mmsc.getContext();
    }

    public MMSCServer getMmsc() {
        return mmsc;
    }

    public void setMmsc(MMSCServer mmsc) {
        this.mmsc = mmsc;
    }
}
