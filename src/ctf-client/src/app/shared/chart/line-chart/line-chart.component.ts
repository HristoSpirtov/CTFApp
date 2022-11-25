import { Component, Input, OnInit } from '@angular/core';
import { EChartsOption } from 'echarts';
import { LineChartData } from '../../model/line-chart-model';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.css']
})
export class LineChartComponent implements OnInit {

  @Input() lineChartData : LineChartData[] = [];
  @Input() chartName : string = "";
  @Input() username : string = "";
  lineChartOption! : EChartsOption;
  legendData : string[]=[];

  constructor() {}

  ngOnInit(): void {
    console.log(this.lineChartData);
    console.log(this.legendData);
    this.lineChartData.forEach(x => this.legendData.push(x.name));
    this.lineChartOption  = {
      title: {
        left: "center",
        text: this.chartName,
        top: 0
      },
      tooltip: {
        trigger: "axis",
        borderWidth: 0,
        axisPointer: {
            type: "cross"
        },
        backgroundColor: "rgba(0, 0, 2, 0.6)",
        textStyle: {
          color: 'white'
        },
      },
      legendHoverLink : true,
      legend: {
          type: "scroll",
          bottom: 0,
      },
      toolbox: {
        feature: {
            saveAsImage: {}
        }
      },
      grid: {
        top: 100,
        containLabel: !0  
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: this.legendData,
      },
      
      yAxis: {
        type: 'value',
      },
      dataZoom: [
        {
          type: "slider",
          xAxisIndex: [0],
          filterMode: "filter",
          height: 20,
          top: 40,
          fillerColor: "rgba(233, 236, 241, 0.4)"
        },
        {
          type: 'inside',
          zoomOnMouseWheel: true
        }
     ],
      series: {
          name: this.username,
          type: "line",
          symbolSize: 5,
          label: {
            show: !0,
            position: "top",
            fontSize : 12,
            offset : [0, -5],
            color : 'rgba(63, 154, 224, 0.7)',
          },
          areaStyle: {
            color: "rgba(103,209,242,255)"   
          },
          emphasis : {
            disabled : false,
            scale : 2.5
          },
          data: this.lineChartData
        }
    };
  }
}
