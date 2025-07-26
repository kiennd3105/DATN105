let currentPage = 0;
const size = 5;

function formatDate(isoString) {
    if (!isoString) return '(Không có)';
    const d = new Date(isoString);
    if (isNaN(d.getTime())) return '(Không hợp lệ)';
    return d.toLocaleDateString('vi-VN') + ' ' + d.toLocaleTimeString('vi-VN', {
        hour: '2-digit',
        minute: '2-digit'
    });
}

function loadVouchers(page = 0) {
    currentPage = page;
    document.getElementById('listSection').style.display = '';
    document.getElementById('formSection').style.display = 'none';

    const keyword = document.getElementById('keyword').value;
    const status = document.getElementById('status').value;

    fetch(`/api/vouchers?page=${page}&size=${size}&keyword=${keyword}&status=${status}`)
        .then(res => res.json())
        .then(data => {
            renderTable(data.content);
            renderPagination(data);
        });
}

function submitForm(e) {
    e.preventDefault();

    const ngayBatDau = document.getElementById('ngaybatdau').value;
    const ngayHetHan = document.getElementById('ngayhethan').value;

    if (ngayBatDau && ngayHetHan && new Date(ngayBatDau) > new Date(ngayHetHan)) {
        alert("Ngày bắt đầu phải trước ngày hết hạn.");
        return;
    }

    if (parseFloat(document.getElementById('giatrigiam').value) <= 0) {
        alert("Giá trị giảm phải lớn hơn 0.");
        return;
    }

    if (parseFloat(document.getElementById('dieukien').value) < 0) {
        alert("Điều kiện không được nhỏ hơn 0.");
        return;
    }

    const voucher = {
        id: document.getElementById('id').value,
        mavc: document.getElementById('mavc').value,
        code: document.getElementById('code').value,
        giatrigiam: document.getElementById('giatrigiam').value,
        dieukien: document.getElementById('dieukien').value,
        trangthai: document.getElementById('trangthai').value,
        loaivc: document.getElementById('loaivc').value,
        soluong: document.getElementById('soluong').value,
        loaigiamgia: document.getElementById('loaigiamgia').value,
        ngayBatDau: ngayBatDau,
        ngayHetHan: ngayHetHan,

        //  THÊM NGAY DÒNG NÀY
        // ngaytao: document.getElementById('ngaytao').value
    };

    fetch('/api/vouchers/save', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(voucher)
    })
        .then(res => {
            if (!res.ok) throw new Error('Lỗi khi lưu');
            return res.json();
        })
        .then(() => {
            loadVouchers(currentPage);
        })
        .catch(err => alert(err));
}


function editVoucher(id) {
    fetch(`/api/vouchers/${id}`)
        .then(res => res.json())
        .then(v => {
            document.getElementById('formTitle').innerText = 'Chỉnh sửa Voucher';
            document.getElementById('formSection').style.display = '';
            document.getElementById('listSection').style.display = 'none';

            document.getElementById('id').value = v.id;
            document.getElementById('mavc').value = v.mavc;
            document.getElementById('code').value = v.code;
            document.getElementById('giatrigiam').value = v.giatrigiam;
            document.getElementById('dieukien').value = v.dieukien;
            document.getElementById('trangthai').value = v.trangthai;
            document.getElementById('loaivc').value = v.loaivc ?? '';
            document.getElementById('soluong').value = v.soluong ?? '';
            document.getElementById('loaigiamgia').value = v.loaigiamgia ?? '0';
            document.getElementById('ngaybatdau').value = v.ngayBatDau ?? '';
            document.getElementById('ngayhethan').value = v.ngayHetHan ?? '';

            // ✅ Ngày tạo và ngày sửa
            if (v.ngaytao) {
                document.getElementById('ngaytao').value = v.ngaytao.substring(0, 16);
            } else {
                document.getElementById('ngaytao').value = '';
            }

            if (v.ngaysua) {
                document.getElementById('ngaysua').value = v.ngaysua.substring(0, 16);
            } else {
                document.getElementById('ngaysua').value = '';
            }

            // Cho phép chỉnh sửa trừ ngày tạo/ngày sửa
            document.querySelectorAll('#formSection input, #formSection select').forEach(el => {
                if (el.id !== 'ngaytao' && el.id !== 'ngaysua') el.removeAttribute('disabled');
            });

            document.querySelector('#formSection button[type=\"submit\"]').style.display = '';
            document.getElementById('btnCancel').innerText = 'Huỷ';
        });
}

function viewVoucher(id) {
    fetch(`/api/vouchers/${id}`)
        .then(res => res.json())
        .then(v => {
            document.getElementById('formTitle').innerText = 'Chi tiết Voucher';
            document.getElementById('formSection').style.display = '';
            document.getElementById('listSection').style.display = 'none';

            document.getElementById('id').value = v.id;
            document.getElementById('mavc').value = v.mavc;
            document.getElementById('code').value = v.code;
            document.getElementById('giatrigiam').value = v.giatrigiam;
            document.getElementById('dieukien').value = v.dieukien;
            document.getElementById('trangthai').value = v.trangthai;
            document.getElementById('loaivc').value = v.loaivc ?? '';
            document.getElementById('soluong').value = v.soluong ?? '';
            document.getElementById('loaigiamgia').value = v.loaigiamgia ?? '0';
            document.getElementById('ngaybatdau').value = v.ngayBatDau ?? '';
            document.getElementById('ngayhethan').value = v.ngayHetHan ?? '';
            document.getElementById('ngaytao').value = v.ngaytao ? v.ngaytao.substring(0, 16) : '';
            document.getElementById('ngaysua').value = v.ngaysua ? v.ngaysua.substring(0, 16) : '';

            document.querySelectorAll('#formSection input, #formSection select').forEach(el => {
                el.setAttribute('disabled', 'true');
            });

            document.querySelector('#formSection button[type="submit"]').style.display = 'none';
            document.getElementById('btnCancel').innerText = 'Đóng';
        });
}

function cancelForm() {
    document.querySelectorAll('#formSection input, #formSection select').forEach(el => {
        if (el.id !== 'ngaytao' && el.id !== 'ngaysua') {
            el.removeAttribute('disabled');
        }
    });
    document.querySelector('#formSection button[type="submit"]').style.display = '';
    document.getElementById('btnCancel').innerText = 'Huỷ';
    loadVouchers(currentPage);
}

function deleteVoucher(id) {
    if (confirm('Xoá voucher này?')) {
        fetch(`/api/vouchers/delete/${id}`, { method: 'DELETE' })
            .then(res => {
                if (!res.ok) return res.text().then(text => { throw new Error(text); });
                loadVouchers(currentPage);
            })
            .catch(err => alert(err.message));
    }
}


function renderTable(vouchers) {
    const tbody = document.getElementById('voucherTableBody');
    tbody.innerHTML = '';
    if (!vouchers.length) {
        tbody.innerHTML = `<tr><td colspan="7" class="text-muted">Không có dữ liệu.</td></tr>`;
        return;
    }
    vouchers.forEach(v => {
        tbody.innerHTML += `
      <tr>
        <td>${v.mavc}</td>
        <td>${v.code}</td>
        <td>${v.giatrigiam.toLocaleString()}</td>
        <td>${v.dieukien.toLocaleString()}</td>
        <td>${v.trangthai === 1 ? 'Đang hoạt động' : 'Ngưng'}</td>
        <td>${formatDate(v.ngaytao)}</td>
        <td>
          <button class="btn btn-info btn-sm" onclick="editVoucher('${v.id}')">Sửa</button>
          <button class="btn btn-secondary btn-sm" onclick="viewVoucher('${v.id}')">Xem</button>
          <button class="btn btn-danger btn-sm" onclick="deleteVoucher('${v.id}')">Xoá</button>
        </td>
      </tr>`;
    });
}

function renderPagination(data) {
    const ul = document.getElementById('pagination');
    ul.innerHTML = '';
    for (let i = 0; i < data.totalPages; i++) {
        ul.innerHTML += `<li class="page-item ${i === data.number ? 'active' : ''}">
            <a class="page-link" href="#" onclick="loadVouchers(${i})">${i + 1}</a>
        </li>`;
    }
}

function showCreateForm() {
    document.getElementById('formTitle').innerText = 'Tạo mới Voucher';
    document.getElementById('formSection').style.display = '';
    document.getElementById('listSection').style.display = 'none';
    document.querySelector('form').reset();
    document.getElementById('id').value = '';

    const now = new Date();
    document.getElementById('ngaytao').value = now.toISOString().substring(0, 16);
    const ngaysuaField = document.getElementById('ngaysua');
    if (ngaysuaField) {
        ngaysuaField.value = '';
    }

    document.querySelectorAll('#formSection input, #formSection select').forEach(el => {
        if (el.id !== 'ngaytao' && el.id !== 'ngaysua') {
            el.removeAttribute('disabled');
        }
    });

    document.querySelector('#formSection button[type="submit"]').style.display = '';
    document.getElementById('btnCancel').innerText = 'Huỷ';
}

window.onload = () => {
    loadVouchers(0);
};