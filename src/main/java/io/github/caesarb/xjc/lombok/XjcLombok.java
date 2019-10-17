package io.github.caesarb.xjc.lombok;

import java.io.IOException;
import java.util.Map;

import org.xml.sax.ErrorHandler;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.tools.xjc.BadCommandLineException;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.Outline;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class XjcLombok extends Plugin {

    @Override
    public String getOptionName() {
        return "Xxjclombok";
    }

    @Override
    public String getUsage() {
        return "  -Xxjclombok\t:  enable generation of lombok annotations";
    }

    @Override
    public int parseArgument(Options opt, String[] args, int i) throws BadCommandLineException, IOException {
        return 0;
    }

    @Override
    public boolean run(final Outline outline, final Options options, final ErrorHandler errorHandler) {

        for (final ClassOutline classOutline : outline.getClasses()) {
            final JDefinedClass implClass = classOutline.implClass;

            implClass.annotate(ToString.class);
    		implClass.annotate(EqualsAndHashCode.class);
    		implClass.annotate(AllArgsConstructor.class);
    		
    		Map<String, JFieldVar> fields = implClass.fields();
    		if(!fields.isEmpty() && !(fields.containsKey("serialVersionUID") && fields.size() == 1))
    			implClass.annotate(NoArgsConstructor.class);
        }
        return true;
    }
}