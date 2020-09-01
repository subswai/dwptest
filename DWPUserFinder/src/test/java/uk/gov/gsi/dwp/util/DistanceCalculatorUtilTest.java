//package uk.gov.gsi.dwp.util;
//
//import org.junit.jupiter.api.Test;
//import uk.gov.gsi.dwp.model.Location;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//public class DistanceCalculatorUtilTest {
//
//    @Test
//    public void shouldReturnValueForTwoDifferentLocation() {
//        assertThat(DistanceCalculatorUtil.findDistance(
//                51.509865,-0.118092,
//                new Location("London", 53.958332,-1.080278))).isEqualTo(173.88334605308827);
//    }
//
//    @Test
//    public void shouldReturnZeroForSameLocation() {
//        assertThat(DistanceCalculatorUtil.findDistance(
//                51.509865,-0.118092,
//                new Location("London", 51.509865,-0.118092))).isEqualTo(0);
//    }
//
//}
