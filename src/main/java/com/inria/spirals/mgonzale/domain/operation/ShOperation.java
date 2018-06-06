/**
 * Created by Hai on 18/6/5.
 */
package com.inria.spirals.mgonzale.domain.operation;

import com.inria.spirals.mgonzale.domain.ScriptChaos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * <p>脚本操作</p>
 *
 * @author Hai
 * @date 18/6/5 : 9:14
 */
@Component
public class ShOperation extends ScriptChaos{

    @Autowired
    private ResourceLoader resourceLoader;

}
