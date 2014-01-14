package ljdp.easypacket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotating a field with this registers the field to be written and read to the packet data. The type of the field is automatically inferred, however only a
 * limited number of types are recognised.
 *
 * @author lukeperkin
 * @see easypacket.serializer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface EasyPacketData {

}
