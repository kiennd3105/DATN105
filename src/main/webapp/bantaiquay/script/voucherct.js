let currentPage = 0;
const pageSize = 5;

function loadVoucherCT(page = 0) {
    currentPage = page;

    const idvc = document.getElementById('filter_idvc').value.trim();
    const idkh = document.getElementById('filter_idkh').value.trim();
    const idhd = document.getElementById('filter_idhd').value.trim();

    const params = new URLSearchParams({
        page,
        size: pageSize,
        idvc,
        idkh,
        idhd
    });

    fetch(`/api/voucherct/filter?${params}`)
        .then(res => res.json())
        .then(data => {
            renderTable(data.content || []);
            renderPagination(data);
        })
        .catch(err => {
            console.error("Lỗi khi tải dữ liệu:", err);
            document.getElementById("voucherctTableBody").innerHTML =
                '<tr><td colspan="8" class="text-center text-danger">Lỗi khi tải dữ liệu</td></tr>';
        });
}

function renderTable(data) {
    const tbody = document.getElementById('voucherctTableBody');
    tbody.innerHTML = '';

    if (!data || data.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="text-center text-muted">Không có dữ liệu</td></tr>';
        return;
    }

    for (const v of data) {
        tbody.innerHTML += `
            <tr>
                <td>${v.idvc ?? ''}</td>
                <td>${v.idkh ?? ''} - ${v.tenKhachHang ?? ''}</td>
                <td>${v.idhd ?? ''} - ${v.maHoaDon ?? ''}</td>
                <td>${v.trangthai === 1 ? 'Đã dùng' : 'Chưa dùng'}</td>
                <td>${formatDate(v.ngaytao)}</td>
                <td>
                    <button class="btn btn-sm btn-info" onclick="editVoucherCT('${v.id}')">Sửa</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteVoucherCT('${v.id}')">Xoá</button>
                </td>
            </tr>`;
    }
}

function renderPagination(data) {
    const ul = document.getElementById('pagination');
    ul.innerHTML = '';

    for (let i = 0; i < data.totalPages; i++) {
        ul.innerHTML += `
            <li class="page-item ${i === data.currentPage ? 'active' : ''}">
                <a class="page-link" href="#" onclick="loadVoucherCT(${i})">${i + 1}</a>
            </li>`;
    }
}

function formatDate(str) {
    if (!str) return '';
    const d = new Date(str);
    return d.toLocaleString('vi-VN');
}

function showForm(title = 'Thêm mới') {
    document.getElementById('formSection').style.display = '';
    document.getElementById('listSection').style.display = 'none';
    document.getElementById('formTitle').innerText = title;
}

function hideForm() {
    document.getElementById('formSection').style.display = 'none';
    document.getElementById('listSection').style.display = '';
    document.getElementById('voucherctForm').reset();
    document.getElementById('id').value = '';
}

function showCreateForm() {
    showForm('Thêm VoucherCT');
}

function editVoucherCT(id) {
    fetch(`/api/voucherct/${id}`)
        .then(res => res.json())
        .then(data => {
            showForm('Chỉnh sửa VoucherCT');
            document.getElementById('id').value = data.id ?? '';
            document.getElementById('idvc').value = data.idvc ?? '';
            document.getElementById('idkh').value = data.idkh ?? '';
            document.getElementById('idhd').value = data.idhd ?? '';
            document.getElementById('trangthai').value = data.trangthai ?? 0;
            document.getElementById('ngaytao').value = data.ngaytao?.substring(0, 16) ?? '';
            document.getElementById('ngaysua').value = data.ngaysua?.substring(0, 16) ?? '';
        })
        .catch(err => alert("Lỗi khi tải chi tiết: " + err.message));
}

function deleteVoucherCT(id) {
    if (confirm('Bạn có chắc chắn muốn xoá?')) {
        fetch(`/api/voucherct/${id}`, { method: 'DELETE' })
            .then(res => {
                if (!res.ok) throw new Error("Xoá thất bại");
                loadVoucherCT(currentPage);
            })
            .catch(err => alert(err.message));
    }
}

function submitForm(e) {
    e.preventDefault();

    const id = document.getElementById('id').value;

    const body = {
        idvc: document.getElementById('idvc').value,
        idkh: document.getElementById('idkh').value,
        idhd: document.getElementById('idhd').value,
        trangthai: parseInt(document.getElementById('trangthai').value)
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `/api/voucherct/${id}` : '/api/voucherct';

    fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
    })
        .then(res => {
            if (!res.ok) throw new Error("Lỗi khi lưu");
            return res.json();
        })
        .then(() => {
            hideForm();
            loadVoucherCT(currentPage);
        })
        .catch(err => alert(err.message));
}


window.onload = () => {
    loadVoucherCT();
};
