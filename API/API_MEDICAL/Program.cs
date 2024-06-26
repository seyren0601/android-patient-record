using API_MEDICAL.Services;
using API_MEDICAL.Entities;
using Microsoft.AspNetCore.Http.HttpResults;
using System.Net;
using Microsoft.AspNetCore.Mvc;
using API_MEDICAL.Entities.BuoiKham;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSingleton<DbService>();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

var summaries = new[]
{
    "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
};

app.MapGet("/weatherforecast", () =>
{
    var forecast = Enumerable.Range(1, 5).Select(index =>
        new WeatherForecast
        (
            DateOnly.FromDateTime(DateTime.Now.AddDays(index)),
            Random.Shared.Next(-20, 55),
            summaries[Random.Shared.Next(summaries.Length)]
        ))
        .ToArray();
    return forecast;
})
.WithName("GetWeatherForecast")
.WithOpenApi();

#region Get
app.MapGet("/benhnhan/all", (DbService service) =>
{
    return service.GetBenhNhan();
});

app.MapGet("/benhnhan/find", ([FromQuery] string tenBenhNhan, DbService service) =>
{
    return service.GetBenhNhan(tenBenhNhan);
});

app.MapGet("/benh", ([FromQuery] string icd, DbService service) =>
{
    return service.GetBenh(icd);
});

app.MapGet("/benh/all", (DbService service)=>{
    return service.GetAllBenh();
});

app.MapGet("/thuoc/all", (DbService service) =>
{
    return service.GetAllThuoc();
});

app.MapGet("/thuoc", ([FromQuery] string id, DbService service) =>
{
    return service.GetThuoc(id);
});

app.MapGet("/buoikham/all", ([FromQuery] int userID, DbService service) =>
{
    return service.GetAllBuoiKham(userID);
});

app.MapGet("/buoikham/today", (DbService service) =>
{
    return service.GetBuoiKhamHomNay();
});

app.MapGet("/buoikham", ([FromQuery] int id, DbService service) =>
{
    return service.GetBuoiKham(id);
});

app.MapGet("/lieuthuoc", ([FromQuery] int id_buoikham, DbService service) =>
{
    return service.GetLieuThuoc(id_buoikham);
});
#endregion

#region Post
app.MapPost("/benh", (string icd, DbService service) =>
{
    return service.GetBenh(icd);
});

app.MapPost("/thuoc/ten", (string tenThuoc, DbService service) =>
{
    return service.GetThuoc_TenThuoc(tenThuoc);
});

app.MapPost("/thuoc/hoatchat", (string hoatChat, DbService service) =>
{
    return service.GetThuoc_HoatChat(hoatChat);
});

app.MapPost("/benhnhan/ten", (string ten, DbService service) =>
{
    return service.GetBenhNhan(ten);
});

app.MapPost("/benhnhan/cccd", (string cccd, DbService service) =>
{
    return service.GetBenhNhanByCCCD(cccd);
});

app.MapPost("/update/benhnhan", (BenhNhan benhnhan, DbService service) =>{
    return service.UpdateBenhNhan(benhnhan);
});

app.MapPost("buoikham/add", ([FromBody] AddBuoiKhamRequest request, DbService service) =>
{
    return service.InsertBuoiKham(request.buoiKham, request.list_lieuthuoc);
});
#endregion

#region Put
app.MapPut("/them/benhnhan", (BenhNhan benhnhan, DbService service) =>
{
    int affected = service.InsertBenhNhan(benhnhan);
    if (affected == 0) return StatusCodes.Status304NotModified;
    else return StatusCodes.Status201Created;
});
#endregion

#region Delete
app.MapDelete("/delete/benhnhan", ([FromQuery] int ID, DbService service) =>
{
    return service.DeleteBenhNhan(ID);
});
#endregion

app.Run();

internal record WeatherForecast(DateOnly Date, int TemperatureC, string? Summary)
{
    public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);
}

internal record AddBuoiKhamRequest()
{
    public BuoiKham buoiKham { get; set; }
    public List<LieuThuoc> list_lieuthuoc { get; set; }
}
