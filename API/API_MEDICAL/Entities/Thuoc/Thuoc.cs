using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace API_MEDICAL.Entities
{
    [Table("MEDICINE", Schema="PATIENT_RECORD")]
    public class Thuoc
    {
        [Key]
        public string ID { get; set; }
        public string TEN_THUOC { get; set; }
        public string HOAT_CHAT { get; set; }
    }
}
