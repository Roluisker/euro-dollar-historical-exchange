package com.sc.timeline.model

import lecho.lib.hellocharts.model.AxisValue
import lecho.lib.hellocharts.model.PointValue
import java.util.ArrayList

open class GraphLineData(
    var axisValues: ArrayList<AxisValue>,
    var yAxisValues: ArrayList<PointValue>
)