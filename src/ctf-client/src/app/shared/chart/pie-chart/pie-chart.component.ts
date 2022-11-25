import { Component, Input, OnInit } from '@angular/core';
import { EChartsOption } from 'echarts';
import { PieChartData } from '../../model/pie-chart-data';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.css']
})
export class PieChartComponent implements OnInit {

  @Input() pieChartData : PieChartData[] = [];
  @Input() chartName : string = "";
  legendData! : string[];
  pieChartOption! : EChartsOption;
  solvePercentagecolorPalette : any[] = ["rgb(207, 38, 0)", "rgb(0, 209, 64)"];

  constructor() {
    this.pieChartData.forEach(x => this.legendData.push(x.name));
  }

  ngOnInit(): void {
    
    this.pieChartOption! = {
      title: {
        left: "center",
        text: this.chartName
      },
      tooltip: {
          trigger: "item"
      },
      toolbox: {
          show: !0,
          feature: {
              saveAsImage: {}
          }
      },
      legend: {
          orient: "horizontal",
          bottom: 30,
          right: '33%',
          data: this.legendData
      },
      series: [{
          name: this.chartName,
          type: "pie",
          radius: ["30%", "50%"],
          avoidLabelOverlap: !0,
          color: this.chartName === 'Solve Percentages' ?  this.solvePercentagecolorPalette : [],
          label: {
              show: !0,
              position: 'outside',
              formatter: (data: any) => {
                return data.name + ' - ' +  data.value + ' (' + data.percent + '%' + ')';
            }
          },
          labelLine: {
            show: !0
          },
          emphasis : {
            scale : true,
            scaleSize: 10,
            label : {
              show : !0,
              fontSize : '14px',
              fontWeight : 'bold'
            },
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            },
          },
          animationEasing: "cubicIn",
          animationDelay: 100,
          tooltip: {
            formatter : '{a}<br />{b}: {c}',
            borderWidth: 0,
            backgroundColor: "rgba(0, 0, 2, 0.6)",
            textStyle: {
              color: 'white'
            },
          },
          data : this.pieChartData
      }]
    };
  }

}
