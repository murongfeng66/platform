function DatePicker() {
}

(() => {
    DatePicker.init = function () {
        document.querySelectorAll('input.date-picker').forEach(function ($input) {
            let id = $input.getAttribute('id');
            id = id || $input.getAttribute('name');
            id += '_date_picker';
            $input.setAttribute('autocomplete', 'off');
            $input.onfocus = () => {

                let $datePicker = document.getElementById(id);
                if (!$datePicker) {
                    $datePicker = document.createElement('div');
                    $datePicker.id = id;
                    $datePicker.classList.add('date-picker-body');
                    $input.parentNode.appendChild($datePicker);
                    let tableBodyId = `${id}_body_table_body`;
                    let topYearId = `${id}_body_top_year`;
                    let topMonthId = `${id}_body_top_month`;

                    $datePicker.innerHTML = `<div class="date-picker-top">
                                                <span class="prefixYear fa fa-angle-double-left"></span>
                                                <span class="prefixMonth fa fa-angle-left"></span>
                                                <span id="${topYearId}"></span>
                                                <span id="${topMonthId}"></span>
                                                <span class="nextMonth fa fa-angle-right"></span>
                                                <span class="nextYear fa fa-angle-double-right"></span>
                                             </div>
                                             <table class="date-picker-body-table">
                                                <thead><tr><th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th></tr></thead>
                                                <tbody id="${tableBodyId}" class="date-picker-body-table-body"></tbody>
                                             </table>`;

                    $datePicker.querySelector('.prefixYear').onclick = function () {
                        createPickerBody(id, -12);
                    };
                    $datePicker.querySelector('.prefixMonth').onclick = function () {
                        createPickerBody(id, -1);
                    };
                    $datePicker.querySelector('.nextYear').onclick = function () {
                        createPickerBody(id, 12);
                    };
                    $datePicker.querySelector('.nextMonth').onclick = function () {
                        createPickerBody(id, 1);
                    };
                    document.getElementById(tableBodyId).onclick = function () {
                        window.event.stopPropagation();
                        let target = window.event.target;
                        let currentActive = document.getElementById(tableBodyId).querySelector('.active');
                        if (currentActive) {
                            currentActive.classList.remove('active');
                        }
                        target.classList.add('active');
                        $datePicker.style.display = '';

                        let year = document.getElementById(topYearId).innerText;
                        let month = document.getElementById(topMonthId).innerText;
                        if (target.classList.contains('prefix')) {
                            month--;
                        }
                        if (target.classList.contains('next')) {
                            month++;
                        }
                        if (month === 0) {
                            month = 12;
                            year--;
                        }
                        if (month === 13) {
                            month = 1;
                            year++;
                        }
                        month = month.fixLength(2, '0', 'prefix');
                        let day = target.innerText.fixLength(2, '0', 'prefix');
                        let selected = `${year}-${month}-${day}`;
                        $input.setValue(selected);
                        $datePicker.setAttribute('data-selected', selected);
                    }
                }

                $datePicker.setAttribute('data-selected', $input.value);
                createPickerBody(id, 0);
                $datePicker.style.top = ($input.offsetTop + $input.offsetHeight) + 'px';
                $datePicker.style.left = $input.offsetLeft + 'px';
                $datePicker.style.display = 'block';
            };
            $input.addEventListener('blur', () => {
                document.getElementById(id).style.display = '';
            });
        });
    };


    function createPickerBody(id, monthChangeValue) {
        let tableBodyId = `${id}_body_table_body`;
        let topYearId = `${id}_body_top_year`;
        let topMonthId = `${id}_body_top_month`;
        let $datePicker = document.getElementById(id);

        let now = new Date();

        let currentDate;
        let dateStr = $datePicker.getAttribute('data-selected');
        if (dateStr) {
            currentDate = new Date(dateStr);
        } else {
            currentDate = new Date();
        }

        let currentYear = document.getElementById(topYearId).innerText;
        currentYear = currentYear || currentDate.getFullYear();
        currentDate.setFullYear(currentYear);
        let currentMonth = document.getElementById(topMonthId).innerText;
        currentMonth = currentMonth ? currentMonth - 1 : currentDate.getMonth();
        currentDate.setMonth(currentMonth + monthChangeValue);

        //是否是现在的月份
        let isNow = now.getFullYear() === currentDate.getFullYear() && now.getMonth() === currentDate.getMonth();
        let isCurrent = false;
        let date;
        if (dateStr) {
            date = new Date(dateStr);
            isCurrent = date.getFullYear() === currentDate.getFullYear() && date.getMonth() === currentDate.getMonth();
        }
        document.getElementById(topYearId).innerText = currentDate.getFullYear();
        document.getElementById(topMonthId).innerText = (currentDate.getMonth() + 1).fixLength(2, '0', 'prefix');

        currentDate.setDate(1);
        let beginWeek = currentDate.getDay();
        currentDate.setMonth(currentDate.getMonth() + 1, 0);
        let monthMaxDay = currentDate.getDate();
        let days = [];
        currentDate.setDate(0);
        let preMonthMaxDay = currentDate.getDate();

        for (let dayIndex = preMonthMaxDay - beginWeek + 1; dayIndex <= preMonthMaxDay; dayIndex++) {
            days.push(dayIndex);
        }
        let currentBeginIndex = days.length;
        for (let dayIndex = 1; dayIndex <= monthMaxDay; dayIndex++) {
            if (dayIndex > 0) {
                days.push(dayIndex);
            }
        }
        let currentEndIndex = days.length - 1;
        for (let dayIndex = 1; days.length % 7 !== 0; dayIndex++) {
            days.push(dayIndex);
        }

        let htmlString = '';
        days.forEach(function (item, index) {
            let remainder = (index + 1) % 7;
            if (remainder === 1) {
                htmlString += '<tr>';
            }
            let classNames = '';
            if (index < currentBeginIndex) {
                classNames = 'prefix';
            } else if (index > currentEndIndex) {
                classNames = 'next';
            } else {
                classNames = 'current';
                if (isNow && item === now.getDate()) {
                    classNames += ' now';
                }

                if (isCurrent && item === date.getDate()) {
                    classNames += ' active';
                }
            }
            htmlString += `<td class="${classNames}">${item}</td>`;
            if (remainder === 0) {
                htmlString += '</tr>';
            }
        });
        document.getElementById(tableBodyId).innerHTML = htmlString;
    }
})();