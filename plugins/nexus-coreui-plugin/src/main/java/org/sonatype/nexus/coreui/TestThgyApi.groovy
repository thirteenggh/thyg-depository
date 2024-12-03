package org.sonatype.nexus.coreui

import com.softwarementors.extjs.djn.config.annotations.DirectAction
import com.softwarementors.extjs.djn.config.annotations.DirectMethod
import org.sonatype.nexus.extdirect.DirectComponentSupport

import com.codahale.metrics.annotation.ExceptionMetered
import com.codahale.metrics.annotation.Timed
import javax.inject.Named
import javax.inject.Singleton
import org.hibernate.validator.constraints.NotEmpty

@Named
@Singleton
@DirectAction(action = 'test_thgy')
class TestThgyApi
    extends DirectComponentSupport
{
    @DirectMethod
    @Timed
    @ExceptionMetered
    String debug(final @NotEmpty String msg) {
        return "DEBUG: $msg"
    }
}