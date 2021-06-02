package org.nomisng;

import com.foreach.across.config.AcrossApplication;
import com.foreach.across.core.AcrossModule;
import com.foreach.across.core.context.configurer.ComponentScanConfigurer;
import com.foreach.across.modules.hibernate.jpa.AcrossHibernateJpaModule;
import com.foreach.across.modules.web.AcrossWebModule;
import org.lamisplus.modules.bootstrap.BootstrapModule;

@AcrossApplication(
        modules = {
                AcrossWebModule.NAME,
                AcrossHibernateJpaModule.NAME,
                BootstrapModule.NAME
        }
)
public class NomisBaseModule extends AcrossModule {
    public static final String NAME = "NomisBaseModule";

    public NomisBaseModule() {
        super();
        addApplicationContextConfigurer(new ComponentScanConfigurer(
                getClass().getPackage().getName() +".controller",
                getClass().getPackage().getName() +".security",
                getClass().getPackage().getName() +".security.jwt",
                getClass().getPackage().getName() +".service",
                getClass().getPackage().getName() +".interceptor",
                getClass().getPackage().getName() +".config",
                getClass().getPackage().getName() +".domain",
                getClass().getPackage().getName() +".domain.mapper",
                getClass().getPackage().getName() +".util"));
    }

    public String getName() {
        return NAME;
    }
}
