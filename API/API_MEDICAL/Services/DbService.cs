﻿using Microsoft.EntityFrameworkCore.Storage;
using API_MEDICAL.Data;
using API_MEDICAL.Entities;
using FuzzySharp;
using Org.BouncyCastle.Bcpg;
using Microsoft.EntityFrameworkCore;
using System.ComponentModel.DataAnnotations;
using API_MEDICAL.Entities.BuoiKham;

namespace API_MEDICAL.Services
{
    public class DbService
    {
        #region Read
        public Benh GetBenh(string ICD)
        {
            using(var Context = new DatabaseContext())
            {
                Benh benh = Context.DbBenh.FirstOrDefault(x=>x.MA_BENH.Contains(ICD, StringComparison.OrdinalIgnoreCase));
                return benh;
            }
        }

        public List<Benh> GetAllBenh()
        {
            using(var Context = new DatabaseContext())
            {
                return Context.DbBenh.ToList();
            }
        }

        public Thuoc GetThuoc(string ID)
        {
            using(var Context = new DatabaseContext())
            {
                return Context.DbThuoc.FirstOrDefault(th => th.ID == ID);
            }
        }

        public List<Thuoc> GetThuoc_TenThuoc(string tenThuoc)
        {
            using(var Context = new DatabaseContext())
            {
                Context.DbThuoc.Load();
                List<Thuoc> filter = Context.DbThuoc.Local.ToList().Where(x => (Fuzz.Ratio(x.TEN_THUOC, tenThuoc) > 80) 
                                                                                || x.TEN_THUOC.Contains(tenThuoc, StringComparison.OrdinalIgnoreCase))
                    .ToList();
                return filter;
            }
        }

        public List<Thuoc> GetThuoc_HoatChat(string hoatChat)
        {
            using(var Context = new DatabaseContext())
            {
                Context.DbThuoc.Load();
                List<Thuoc> lst_thuoc = Context.DbThuoc.Local.ToList().Where(x => (Fuzz.Ratio(x.HOAT_CHAT, hoatChat) > 80) 
                                                                                   || x.HOAT_CHAT.Contains(hoatChat, StringComparison.OrdinalIgnoreCase))
                    .ToList();
                return lst_thuoc;
            }
        }

        public List<Thuoc> GetAllThuoc()
        {
            using( var Context = new DatabaseContext())
            {
                return Context.DbThuoc.ToList();
            }
        }

        public List<BenhNhan> GetBenhNhan()
        {
            using(var Context = new DatabaseContext())
            {
                List<BenhNhan> lst_benhnhan = Context.DbBenhNhan.ToList();
                return lst_benhnhan;
            }
        }

        public List<BenhNhan> GetBenhNhan(string ten)
        {
            using(var Context = new DatabaseContext())
            {
                List<BenhNhan> lst_benhnhan = Context.DbBenhNhan
                    .Where(x => x.TEN.Contains(ten, StringComparison.OrdinalIgnoreCase))
                    .ToList();
                return lst_benhnhan;
            }
        }

        public BenhNhan? GetBenhNhanByCCCD(string cccd)
        {
            using(var Context = new DatabaseContext())
            {
                BenhNhan? benhnhan = Context.DbBenhNhan
                    .FirstOrDefault(x => x.CCCD != null && x.CCCD.Equals(cccd, StringComparison.OrdinalIgnoreCase));
                return benhnhan;
            }
        }

        public BenhNhan? GetBenhNhanByBHYT(string bhyt)
        {
            using (var Context = new DatabaseContext())
            {
                BenhNhan? benhnhan = Context.DbBenhNhan
                    .FirstOrDefault(x => x.BHYT != null && x.BHYT.Equals(bhyt, StringComparison.OrdinalIgnoreCase));
                if (benhnhan != null) return benhnhan;
            }
            return null;
        }

        public List<BuoiKham> GetAllBuoiKham(int userID)
        {
            using(var Context = new DatabaseContext())
            {
                return Context.DbBuoiKham.Where(bk => bk.ID_BENH_NHAN == userID).OrderByDescending(x=>x.NGAY).ToList();
            }
        }

        public List<BenhNhan> GetBuoiKhamHomNay()
        {
            using (var Context = new DatabaseContext())
            {
                var query = from benhNhan in Context.DbBenhNhan
                            join buoiKham in Context.DbBuoiKham on benhNhan.ID equals buoiKham.ID_BENH_NHAN
                            where buoiKham.NGAY_TAI_KHAM.Date == DateTime.Now.Date
                            select benhNhan;
                List<BenhNhan> list_benhnhan = query.ToList();
                return list_benhnhan;
            }
        }

        public BuoiKham GetBuoiKham(int id)
        {
            using (var Context = new DatabaseContext())
            {
                return Context.DbBuoiKham.FirstOrDefault(bk => bk.ID == id);
            }
        }

        public List<LieuThuoc> GetLieuThuoc(int id_buoikham)
        {
            using (var Context = new DatabaseContext())
            {
                return Context.DbLieuThuoc.Where(lt=>lt.ID_BUOI_KHAM == id_buoikham).ToList();
            }
        }
        #endregion

        #region Create
        public int InsertBenhNhan(BenhNhan benhnhan)
        {
            using (var Context = new DatabaseContext())
            {
                benhnhan.TUOI = DateTime.Now.Year - benhnhan.NGAY_SINH.Year;
                Context.DbBenhNhan.Add(benhnhan);
                try
                {
                    int affected = Context.SaveChanges();
                    return affected;
                }
                catch(Exception ex)
                {
                    Console.WriteLine(ex.Message);
                    return 0;
                }
                
            }
        }

        public int InsertBuoiKham(BuoiKham buoiKham, List<LieuThuoc> list_lieuthuoc)
        {
            using (var Context = new DatabaseContext())
            {
                Context.DbBuoiKham.Add(buoiKham);
                Context.SaveChanges();

                foreach(LieuThuoc lieuThuoc in list_lieuthuoc)
                {
                    lieuThuoc.ID_BUOI_KHAM = buoiKham.ID;
                    Context.DbLieuThuoc.Add(lieuThuoc);
                }
                int affected = Context.SaveChanges();
                return affected;
            }
        }
        #endregion

        #region Update
        public BenhNhan? UpdateBenhNhan(BenhNhan benhnhan)
        {
            using(var context = new DatabaseContext())
            {
                BenhNhan? entity = context.DbBenhNhan.FirstOrDefault(x=>x.ID == benhnhan.ID);
                if (entity == null) return null;
                else
                {
                    benhnhan.TUOI = DateTime.Now.Year - benhnhan.NGAY_SINH.Year;
                    entity.TEN = benhnhan.TEN;
                    entity.CCCD = benhnhan.CCCD;
                    entity.SDT = benhnhan.SDT;
                    entity.NGAY_SINH = benhnhan.NGAY_SINH;
                    entity.TUOI = benhnhan.TUOI;
                    entity.DIA_CHI = benhnhan.DIA_CHI;
                    entity.BHYT = benhnhan.BHYT;
                    entity.GIOI_TINH = benhnhan.GIOI_TINH;
                    entity.NGHE_NGHIEP = benhnhan.NGHE_NGHIEP;
                    context.SaveChanges();
                    return entity;
                }
            }
        }
        #endregion

        #region Delete
        public bool DeleteBenhNhan(int benhnhanID)
        {
            try
            {
                using(var context = new DatabaseContext())
                {
                    BenhNhan benhnhan = context.DbBenhNhan.FirstOrDefault(bn => bn.ID == benhnhanID);
                    context.DbBenhNhan.Remove(benhnhan);
                    context.SaveChanges();
                    return true;
                }
            }
            catch
            {
                return false;
            }
        }
        #endregion
    }
}
