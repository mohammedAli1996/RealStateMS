(function($) {
  'use strict';
  $(function() {

     $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/ContractsDash/res",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
          if ($("#north-america-chart").length) {
            var areaData = {
              labels: ["Active", "Expired", "Canceled"],
              datasets: [{
                  data: [data.count, data.countExpired, data.countCanceled],
                  backgroundColor: [
                     "#4B49AC","#FFC100", "#248AFD",
                  ],
                  borderColor: "rgba(0,0,0,0)"
                }
              ]
            };
            var areaOptions = {
              responsive: true,
              maintainAspectRatio: true,
              segmentShowStroke: false,
              cutoutPercentage: 78,
              elements: {
                arc: {
                    borderWidth: 4
                }
              },      
              legend: {
                display: false
              },
              tooltips: {
                enabled: true
              },
              legendCallback: function(chart) { 
                var text = [];
                text.push('<div class="report-chart">');
                  text.push('<div class="d-flex justify-content-between mx-4 mx-xl-5 mt-3"><div class="d-flex align-items-center"><div class="mr-3" style="width:20px; height:20px; border-radius: 50%; background-color: ' + chart.data.datasets[0].backgroundColor[0] + '"></div><p class="mb-0">Active</p></div>');
                  text.push("<p class='mb-0'>"+data.count+"</p>");
                  text.push('</div>');
                  text.push('<div class="d-flex justify-content-between mx-4 mx-xl-5 mt-3"><div class="d-flex align-items-center"><div class="mr-3" style="width:20px; height:20px; border-radius: 50%; background-color: ' + chart.data.datasets[0].backgroundColor[1] + '"></div><p class="mb-0">Expired</p></div>');
                  text.push("<p class='mb-0'>"+data.countExpired+"</p>");
                  text.push('</div>');
                  text.push('<div class="d-flex justify-content-between mx-4 mx-xl-5 mt-3"><div class="d-flex align-items-center"><div class="mr-3" style="width:20px; height:20px; border-radius: 50%; background-color: ' + chart.data.datasets[0].backgroundColor[2] + '"></div><p class="mb-0">Canceled</p></div>');
                  text.push("<p class='mb-0'>"+data.countCanceled+"</p>");
                  text.push('</div>');
                text.push('</div>');
                return text.join("");
              },
            }
            var northAmericaChartPlugins = {
              beforeDraw: function(chart) {
                var width = chart.chart.width,
                    height = chart.chart.height,
                    ctx = chart.chart.ctx;
            
                ctx.restore();
                var fontSize = 3.125;
                ctx.font = "500 " + fontSize + "em sans-serif";
                ctx.textBaseline = "middle";
                ctx.fillStyle = "#13381B";
            
                var text = data.total,
                    textX = Math.round((width - ctx.measureText(text).width) / 2),
                    textY = height / 2;
            
                ctx.fillText(text, textX, textY);
                ctx.save();
              }
            }
            var northAmericaChartCanvas = $("#north-america-chart").get(0).getContext("2d");
            var northAmericaChart = new Chart(northAmericaChartCanvas, {
              type: 'doughnut',
              data: areaData,
              options: areaOptions,
              plugins: northAmericaChartPlugins
            });
            document.getElementById('north-america-legend').innerHTML = northAmericaChart.generateLegend();
          }
        },
        error: function (e) {
        }
    });




    $.ajax({
      type: "GET",
      contentType: "application/json",
      url: "/ContractsDash/comm",
      dataType: 'json',
      cache: false,
      timeout: 600000,
      success: function (data) {
        if ($("#south-america-chart").length) {
          var areaData = {
            labels: ["Active", "Expired", "Canceled"],
            datasets: [{
                data: [data.count, data.countExpired, data.countCanceled],
                backgroundColor: [
                   "#4B49AC","#FFC100", "#248AFD",
                ],
                borderColor: "rgba(0,0,0,0)"
              }
            ]
          };
          var areaOptions = {
            responsive: true,
            maintainAspectRatio: true,
            segmentShowStroke: false,
            cutoutPercentage: 78,
            elements: {
              arc: {
                  borderWidth: 4
              }
            },      
            legend: {
              display: false
            },
            tooltips: {
              enabled: true
            },
            legendCallback: function(chart) { 
              var text = [];
              text.push('<div class="report-chart">');
                text.push('<div class="d-flex justify-content-between mx-4 mx-xl-5 mt-3"><div class="d-flex align-items-center"><div class="mr-3" style="width:20px; height:20px; border-radius: 50%; background-color: ' + chart.data.datasets[0].backgroundColor[0] + '"></div><p class="mb-0">Active</p></div>');
                text.push("<p class='mb-0'>"+data.count+"</p>");
                text.push('</div>');
                text.push('<div class="d-flex justify-content-between mx-4 mx-xl-5 mt-3"><div class="d-flex align-items-center"><div class="mr-3" style="width:20px; height:20px; border-radius: 50%; background-color: ' + chart.data.datasets[0].backgroundColor[1] + '"></div><p class="mb-0">Expired</p></div>');
                text.push("<p class='mb-0'>"+data.countExpired+"</p>");
                text.push('</div>');
                text.push('<div class="d-flex justify-content-between mx-4 mx-xl-5 mt-3"><div class="d-flex align-items-center"><div class="mr-3" style="width:20px; height:20px; border-radius: 50%; background-color: ' + chart.data.datasets[0].backgroundColor[2] + '"></div><p class="mb-0">Canceled</p></div>');
                text.push("<p class='mb-0'>"+data.countCanceled+"</p>");
                text.push('</div>');
              text.push('</div>');
              return text.join("");
            },
          }
          var northAmericaChartPlugins = {
            beforeDraw: function(chart) {
              var width = chart.chart.width,
                  height = chart.chart.height,
                  ctx = chart.chart.ctx;
          
              ctx.restore();
              var fontSize = 3.125;
              ctx.font = "500 " + fontSize + "em sans-serif";
              ctx.textBaseline = "middle";
              ctx.fillStyle = "#13381B";
          
              var text = data.total,
                  textX = Math.round((width - ctx.measureText(text).width) / 2),
                  textY = height / 2;
          
              ctx.fillText(text, textX, textY);
              ctx.save();
            }
          }
          var northAmericaChartCanvas = $("#south-america-chart").get(0).getContext("2d");
          var northAmericaChart = new Chart(northAmericaChartCanvas, {
            type: 'doughnut',
            data: areaData,
            options: areaOptions,
            plugins: northAmericaChartPlugins
          });
          document.getElementById('south-america-legend').innerHTML = northAmericaChart.generateLegend();
        }
      },
      error: function (e) {
      }
  });


    function format ( d ) {
      // `d` is the original data object for the row
      return '<table cellpadding="5" cellspacing="0" border="0" style="width:100%;">'+
          '<tr class="expanded-row">'+
              '<td colspan="8" class="row-bg"><div><div class="d-flex justify-content-between"><div class="cell-hilighted"><div class="d-flex mb-2"><div class="mr-2 min-width-cell"><p>Policy start date</p><h6>25/04/2020</h6></div><div class="min-width-cell"><p>Policy end date</p><h6>24/04/2021</h6></div></div><div class="d-flex"><div class="mr-2 min-width-cell"><p>Sum insured</p><h5>$26,000</h5></div><div class="min-width-cell"><p>Premium</p><h5>$1200</h5></div></div></div><div class="expanded-table-normal-cell"><div class="mr-2 mb-4"><p>Quote no.</p><h6>Incs234</h6></div><div class="mr-2"><p>Vehicle Reg. No.</p><h6>KL-65-A-7004</h6></div></div><div class="expanded-table-normal-cell"><div class="mr-2 mb-4"><p>Policy number</p><h6>Incsq123456</h6></div><div class="mr-2"><p>Policy number</p><h6>Incsq123456</h6></div></div><div class="expanded-table-normal-cell"><div class="mr-2 mb-3 d-flex"><div class="highlighted-alpha"> A</div><div><p>Agent / Broker</p><h6>Abcd Enterprices</h6></div></div><div class="mr-2 d-flex"> <img src="../../images/faces/face5.jpg" alt="profile"/><div><p>Policy holder Name & ID Number</p><h6>Phillip Harris / 1234567</h6></div></div></div><div class="expanded-table-normal-cell"><div class="mr-2 mb-4"><p>Branch</p><h6>Koramangala, Bangalore</h6></div></div><div class="expanded-table-normal-cell"><div class="mr-2 mb-4"><p>Channel</p><h6>Online</h6></div></div></div></div></td>'
          '</tr>'+
      '</table>';
  }

$('#example tbody').on('click', 'td.details-control', function () {
  var tr = $(this).closest('tr');
  var row = table.row( tr );

  if ( row.child.isShown() ) {
      // This row is already open - close it
      row.child.hide();
      tr.removeClass('shown');
  }
  else {
      // Open this row
      row.child( format(row.data()) ).show();
      tr.addClass('shown');
  }
} );
  
  });
})(jQuery);



