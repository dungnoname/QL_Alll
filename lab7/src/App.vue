<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// Cấu hình API
const API = 'http://localhost:8080/api';

// Trạng thái
const tab = ref(1);
const isEdit = ref(false);

// Dữ liệu danh sách
const categories = ref([]);
const products = ref([]);
const accounts = ref([]);

// Dữ liệu Form
const catForm = ref({ id: '', name: '' });
const prodForm = ref({ id: null, name: '', price: 0, date: '', categoryId: '' });
const accForm = ref({ username: '', password: '', fullname: '', email: '', role: 'USER', enabled: true });

// --- HÀM TẢI DỮ LIỆU ---
const loadData = async () => {
  try {
    const [cRes, pRes, aRes] = await Promise.all([
      axios.get(`${API}/categories`),
      axios.get(`${API}/products`),
      axios.get(`${API}/accounts`)
    ]);
    categories.value = cRes.data;
    products.value = pRes.data;
    accounts.value = aRes.data;
  } catch (err) {
    console.error(err);
    // alert("Lỗi kết nối Backend!"); // Bật lên nếu muốn thông báo
  }
};

const switchTab = (t) => {
  tab.value = t;
  resetForms();
  loadData();
};

const resetForms = () => {
  isEdit.value = false;
  catForm.value = { id: '', name: '' };
  prodForm.value = { id: null, name: '', price: 0, date: '', categoryId: '' };
  accForm.value = { username: '', password: '', fullname: '', email: '', role: 'USER', enabled: true };
};

const formatDate = (d) => d ? d.substring(0, 10) : '';

// --- BÀI 1: CATEGORY ---
const saveCat = async () => {
  try {
    if (isEdit.value) await axios.put(`${API}/categories/${catForm.value.id}`, catForm.value);
    else await axios.post(`${API}/categories`, catForm.value);
    alert("Thành công!"); loadData(); resetForms();
  } catch (e) { alert(e.response?.data || "Lỗi!"); }
};
const deleteCat = async (id) => {
  if (confirm("Xóa loại hàng này?")) { await axios.delete(`${API}/categories/${id}`); loadData(); }
};
const editCat = (item) => { catForm.value = { ...item }; isEdit.value = true; };

// --- BÀI 2: PRODUCT ---
const saveProd = async () => {
  try {
    if (isEdit.value) await axios.put(`${API}/products/${prodForm.value.id}`, prodForm.value);
    else await axios.post(`${API}/products`, prodForm.value);
    alert("Thành công!"); loadData(); resetForms();
  } catch (e) { alert(e.response?.data || "Lỗi!"); }
};
const deleteProd = async (id) => {
  if (confirm("Xóa sản phẩm này?")) { await axios.delete(`${API}/products/${id}`); loadData(); }
};
const editProd = (item) => {
  prodForm.value = { ...item };
  if (item.date) prodForm.value.date = item.date.substring(0, 10);
  isEdit.value = true;
};

// --- BÀI 3: ACCOUNT ---
const saveAcc = async () => {
  try {
    if (!accForm.value.username) { alert("Nhập username!"); return; }

    if (isEdit.value) await axios.put(`${API}/accounts/${accForm.value.username}`, accForm.value);
    else await axios.post(`${API}/accounts`, accForm.value);
    alert("Thành công!"); loadData(); resetForms();
  } catch (e) { alert(e.response?.data || "Lỗi: Username đã tồn tại!"); }
};
const deleteAcc = async (username) => {
  if (confirm("Xóa tài khoản này?")) { await axios.delete(`${API}/accounts/${username}`); loadData(); }
};
const editAcc = (item) => { accForm.value = { ...item }; isEdit.value = true; };

// Chạy khi component được gắn vào DOM
onMounted(() => loadData());
</script>

<template>
  <div class="app-container">
    <h2 style="text-align: center; color: #333;">LAB 7: CATEGORY - PRODUCT - ACCOUNT</h2>

    <div class="tabs">
      <button class="tab-btn" :class="{ active: tab === 1 }" @click="switchTab(1)">1. Loại Hàng</button>
      <button class="tab-btn" :class="{ active: tab === 2 }" @click="switchTab(2)">2. Sản Phẩm</button>
      <button class="tab-btn" :class="{ active: tab === 3 }" @click="switchTab(3)">3. Tài Khoản</button>
    </div>

    <div v-if="tab === 1">
      <div class="form-box">
        <h3>Quản Lý Loại Hàng</h3>
        <div class="form-row"><label>Mã loại:</label> <input v-model="catForm.id" :disabled="isEdit"></div>
        <div class="form-row"><label>Tên loại:</label> <input v-model="catForm.name"></div>
        <div class="btn-group">
          <button class="btn-save" @click="saveCat">{{ isEdit ? 'Cập nhật' : 'Thêm mới' }}</button>
          <button class="btn-reset" @click="resetForms">Làm mới</button>
        </div>
      </div>
      <table>
        <thead>
        <tr>
          <th>Mã Loại</th>
          <th>Tên Loại</th>
          <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="c in categories" :key="c.id">
          <td>{{ c.id }}</td>
          <td>{{ c.name }}</td>
          <td>
            <span class="action-link text-blue" @click="editCat(c)">Sửa</span>
            <span class="action-link text-red" @click="deleteCat(c.id)">Xóa</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div v-if="tab === 2">
      <div class="form-box">
        <h3>Quản Lý Sản Phẩm</h3>
        <div class="form-row"><label>Tên SP:</label> <input v-model="prodForm.name"></div>
        <div class="form-row"><label>Giá:</label> <input type="number" v-model="prodForm.price"></div>
        <div class="form-row"><label>Ngày nhập:</label> <input type="date" v-model="prodForm.date"></div>
        <div class="form-row">
          <label>Loại hàng:</label>
          <select v-model="prodForm.categoryId">
            <option value="">-- Chọn loại hàng --</option>
            <option v-for="c in categories" :key="c.id" :value="c.id">{{ c.name }}</option>
          </select>
        </div>
        <div class="btn-group">
          <button class="btn-save" @click="saveProd">{{ isEdit ? 'Cập nhật' : 'Thêm mới' }}</button>
          <button class="btn-reset" @click="resetForms">Làm mới</button>
        </div>
      </div>
      <table>
        <thead>
        <tr>
          <th>Tên SP</th>
          <th>Giá</th>
          <th>Ngày nhập</th>
          <th>Mã Loại</th>
          <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="p in products" :key="p.id">
          <td>{{ p.name }}</td>
          <td>{{ p.price }}</td>
          <td>{{ formatDate(p.date) }}</td>
          <td>{{ p.categoryId }}</td>
          <td>
            <span class="action-link text-blue" @click="editProd(p)">Sửa</span>
            <span class="action-link text-red" @click="deleteProd(p.id)">Xóa</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <div v-if="tab === 3">
      <div class="form-box">
        <h3>Quản Lý Tài Khoản</h3>
        <div class="form-row"><label>Username:</label> <input v-model="accForm.username" :disabled="isEdit"
                                                              placeholder="Tên đăng nhập..."></div>
        <div class="form-row"><label>Password:</label> <input v-model="accForm.password" type="password"></div>
        <div class="form-row"><label>Họ tên:</label> <input v-model="accForm.fullname"></div>
        <div class="form-row"><label>Email:</label> <input v-model="accForm.email"></div>
        <div class="form-row">
          <label>Vai trò:</label>
          <div style="display: flex; gap: 15px;">
            <label><input type="radio" value="USER" v-model="accForm.role"> USER</label>
            <label><input type="radio" value="ADMIN" v-model="accForm.role"> ADMIN</label>
          </div>
        </div>
        <div class="btn-group">
          <button class="btn-save" @click="saveAcc">{{ isEdit ? 'Cập nhật' : 'Thêm mới' }}</button>
          <button class="btn-reset" @click="resetForms">Làm mới</button>
        </div>
      </div>
      <table>
        <thead>
        <tr>
          <th>Username</th>
          <th>Họ tên</th>
          <th>Email</th>
          <th>Vai trò</th>
          <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="a in accounts" :key="a.username">
          <td>{{ a.username }}</td>
          <td>{{ a.fullname }}</td>
          <td>{{ a.email }}</td>
          <td>
              <span :style="{ fontWeight: 'bold', color: a.role === 'ADMIN' ? 'red' : 'green' }">
                {{ a.role }}
              </span>
          </td>
          <td>
            <span class="action-link text-blue" @click="editAcc(a)">Sửa</span>
            <span class="action-link text-red" @click="deleteAcc(a.username)">Xóa</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.app-container {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 900px;
  margin: 0 auto;
  background-color: #f4f4f9;
}

.tabs {
  display: flex;
  margin-bottom: 20px;
  border-bottom: 2px solid #ddd;
}

.tab-btn {
  padding: 12px 24px;
  cursor: pointer;
  border: none;
  background: #e0e0e0;
  font-weight: bold;
  margin-right: 4px;
  border-radius: 4px 4px 0 0;
  transition: 0.3s;
}

.tab-btn:hover {
  background: #d0d0d0;
}

.tab-btn.active {
  background: #007bff;
  color: white;
}

.form-box {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.form-row {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

label {
  width: 120px;
  font-weight: bold;
  color: #333;
}

input,
select {
  padding: 8px;
  flex: 1;
  border: 1px solid #ccc;
  border-radius: 4px;
  max-width: 400px;
}

.btn-group {
  margin-top: 20px;
  padding-left: 120px;
}

button {
  padding: 10px 20px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  color: white;
  margin-right: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.btn-save {
  background: #28a745;
}

.btn-reset {
  background: #6c757d;
}

table {
  width: 100%;
  border-collapse: collapse;
  background: white;
}

th,
td {
  border: 1px solid #eee;
  padding: 12px;
  text-align: left;
}

th {
  background: #007bff;
  color: white;
}

.action-link {
  cursor: pointer;
  font-weight: bold;
  text-decoration: underline;
  margin-right: 10px;
}

.text-blue {
  color: #007bff;
}

.text-red {
  color: #dc3545;
}
</style>
